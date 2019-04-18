package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.FinalExamMapper;
import edu.qit.cloudclass.dao.ScoreMapper;
import edu.qit.cloudclass.dao.StudyMapper;
import edu.qit.cloudclass.domain.*;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.service.ExamService;
import edu.qit.cloudclass.service.FinalExamService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.QuestionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinalExamServiceImpl implements FinalExamService {
    private final CourseMapper courseMapper;
    private final StudyMapper studyMapper;
    private final ScoreMapper scoreMapper;
    private final FinalExamMapper finalExamMapper;
    private final PermissionService permissionService;
    private final QuestionService questionService;
    private final ExamService examService;


    @Override
    public ServerResponse create(FinalExam exam, String userId) {

        exam.setId(Tool.uuid());
        //查询课程信息
        Course course = courseMapper.findCourseByPrimaryKey(exam.getCourse());
        if (course == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "课程不存在");
        }
        if (!course.getTeacher().equals(userId)) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "权限不足");
        }
        //题目信息处理
        List<Question> choiceList = examService.parserQuestion(exam.getChoiceList(), exam.getId(), Question.CHOICE_QUESTION);
        if (choiceList == null || choiceList.size() < 1) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "有效问题不足");
        }
        List<Question> judgementList = examService.parserQuestion(exam.getJudgementList(), exam.getId(), Question.JUDGEMENT_QUESTION);
        if (judgementList == null || judgementList.size() < 1) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "有效问题不足");
        }
        //级联删除
        if (course.getFinalExam() != null) {
            associateDelete(course.getFinalExam());
        }
        //创建期末考试
        log.info("==========创建期末考试开始==========");
        log.info("创建期末考试:" + exam.toString());
        if (courseMapper.updateFinalExamAfterUpload(course.getId(), exam.getId()) == 0) {
            log.error("==========考试信息更新失败==========");
            return ServerResponse.createByError("创建失败");
        }
        if (finalExamMapper.insert(exam) == 0) {
            log.error("==========试卷信息创建失败==========");
            return ServerResponse.createByError("创建失败");
        }
        log.info("有效选择题数目:" + choiceList.size());
        questionService.insertList(choiceList);
        log.info("有效判断题数目:" + choiceList.size());
        questionService.insertList(judgementList);
        log.info("==========创建期末考试结束==========");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse page(String examId, String userId) {
        FinalExam exam = finalExamMapper.findExamByPrimaryKey(examId);
        if (exam == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "考试不存在");
        }
        if (exam.isSubmittable()){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不在考试时间");
        }
        ServerResponse permissionResult = permissionService.checkCourseMemberPermission(userId,exam.getCourse());
        if (!permissionResult.isSuccess()){
            return permissionResult;
        }
        exam = (FinalExam) examService.shuffleQuestionList(exam);
        return ServerResponse.createBySuccess("查询成功", exam);
    }

    @Override
    public ServerResponse submit(AnswerComplex answer) {

        FinalExam exam = finalExamMapper.findExamByPrimaryKey(answer.getExam());
        if (exam == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "考试不存在");
        }
        if (!exam.isSubmittable()) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不在考试时间");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(exam.getCourse(), answer.getUser());
        if (study == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "未参加学习");
        }
        Score oldScore = scoreMapper.findScoreByStudyAndExam(study.getId(), exam.getId());
        if (oldScore != null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不可重复提交");
        }
        //更新记录
        Score score = new Score();
        score.setStudy(study.getId());
        score.setExam(answer.getExam());
        score.setScore(examService.countScore(answer));
        log.info("更新成绩:" + score);
        if (scoreMapper.insert(score) == 0) {
            return ServerResponse.createByError("提交失败");
        }
        return ServerResponse.createBySuccess("提交成功", score);
    }

    @Override
    public ServerResponse score(String userId, String courseId) {
        Course course = courseMapper.findCourseByPrimaryKey(courseId);
        if (course == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "课程不存在");
        }
        if (course.getFinalExam() == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "考试不存在");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(courseId, userId);
        if (study == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "未参加学习");
        }
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(), course.getFinalExam());
        if (score == null) {
            return ServerResponse.createByError("暂无成绩");
        }
        return ServerResponse.createBySuccess("查询成功", score);
    }

    @Override
    public void associateDelete(String examId) {
        //记录查询
        FinalExam exam = finalExamMapper.findExamByPrimaryKey(examId);
        if (exam == null) {
            log.warn("考试记录获取失败");
            return;
        }
        //级联删除
        questionService.associateDelete(exam.getId());
        //删除记录
        log.info("删除考试:" + exam.toString());
        if (finalExamMapper.delete(exam.getId()) == 0) {
            log.warn("考试记录删除失败");
        }
    }
}
