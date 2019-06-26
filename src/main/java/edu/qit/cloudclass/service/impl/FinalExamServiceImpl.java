package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.FinalExamMapper;
import edu.qit.cloudclass.dao.StudyMapper;
import edu.qit.cloudclass.domain.*;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.service.*;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final FinalExamMapper finalExamMapper;
    private final QuestionService questionService;
    private final ExamService examService;
    private final ScoreService scoreService;

    @Override
    public ServerResponse create(FinalExam exam) {
        exam.setId(Tool.uuid());
        exam = (FinalExam) examService.parserQuestion(exam);
        if (exam.getChoiceList().isEmpty() && exam.getJudgementList().isEmpty()){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"有效题目不足");
        }
        exam = (FinalExam) examService.parserQuestionScore(exam);
        //级联删除
        Course course = courseMapper.findCourseByPrimaryKey(exam.getCourse());
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
        log.info("有效选择题数目:" + exam.getChoiceList().size());
        questionService.insertList(exam.getChoiceList());
        log.info("有效判断题数目:" + exam.getJudgementList().size());
        questionService.insertList(exam.getJudgementList());
        log.info("==========创建期末考试结束==========");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse page(String examId) {
        FinalExam exam = finalExamMapper.findExamByPrimaryKey(examId);
        if (!exam.isSubmittable()){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不在考试时间");
        }
        exam = (FinalExam) examService.shuffleQuestionList(exam);
        return ServerResponse.createBySuccess("查询成功", exam);
    }

    @Override
    public ServerResponse submit(String userId,String examId,AnswerComplex answer) {
        FinalExam exam = finalExamMapper.findExamByPrimaryKey(examId);
        if (!exam.isSubmittable()) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不在考试时间");
        }
        String courseId = courseMapper.fingCourseIdByFinalExam(examId);
        Study study = studyMapper.findStudyByCourseAndStudent(courseId, userId);
        if (study == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"未参加学习");
        }
        Score score = scoreService.findScoreByStudyAndExam(study.getId(),exam.getId());
        if (score != null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不可重复提交");
        }
        //创建记录
        score = new Score();
        score.setStudy(study.getId());
        score.setExam(examId);
        score.setScore(examService.countScore(answer,examId));
        return scoreService.insert(score);
    }

    @Override
    public ServerResponse score(String userId, String examId) {
        String courseId = courseMapper.fingCourseIdByFinalExam(examId);
        Study study = studyMapper.findStudyByCourseAndStudent(courseId, userId);
        Score score = scoreService.findScoreByStudyAndExam(study.getId(),examId);
        if (score == null) {
            return ServerResponse.createBySuccessMsg("暂无成绩");
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
