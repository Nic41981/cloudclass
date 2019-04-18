package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
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
import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-18
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChapterExamServiceImpl implements ChapterExamService {

    private final ChapterExamMapper chapterExamMapper;
    private final ChapterMapper chapterMapper;
    private final CourseMapper courseMapper;
    private final StudyMapper studyMapper;
    private final ScoreMapper scoreMapper;
    private final PermissionService permissionService;
    private final QuestionService questionService;
    private final ExamService examService;
    private final ScoreService scoreService;

    @Override
    public ServerResponse create(ChapterExam exam, String userId) {
        exam.setId(Tool.uuid());
        //查询课程信息
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(exam.getChapter());
        if (chapter == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "章节不存在");
        }
        Course course = courseMapper.findCourseByPrimaryKey(chapter.getCourse());
        if (course == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "课程不存在");
        }
        if (!course.getTeacher().equals(userId)) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "权限不足");
        }
        //题目信息处理
        List<Question> choiceList = examService.parserQuestion(exam.getChoiceList(), exam.getId(), Question.CHOICE_QUESTION);
        if (choiceList == null || choiceList.isEmpty()) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "有效问题不足");
        }
        List<Question> judgementList = examService.parserQuestion(exam.getJudgementList(), exam.getId(), Question.JUDGEMENT_QUESTION);
        if (judgementList == null || judgementList.isEmpty()) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "有效问题不足");
        }
        //级联删除
        if (chapter.getChapterExam() != null) {
            associateDelete(chapter.getChapterExam());
        }
        //创建章节测试
        log.info("==========创建章节测试开始==========");
        log.info("创建章节测试:" + exam.toString());
        if (chapterMapper.updateChapterExamAfterUpload(chapter.getId(), exam.getId()) == 0) {
            log.error("==========考试信息更新失败==========");
            return ServerResponse.createByError("创建失败");
        }
        if (chapterExamMapper.insert(exam) == 0) {
            log.error("==========试卷信息创建失败==========");
            return ServerResponse.createByError("创建失败");
        }
        log.info("有效选择题数目:" + choiceList.size());
        questionService.insertList(choiceList);
        log.info("有效判断题数目:" + choiceList.size());
        questionService.insertList(judgementList);
        log.info("==========创建章节测试结束==========");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse page(String examId, String userId) {
        ChapterExam exam = chapterExamMapper.findExamByPrimaryKey(examId);
        if (exam == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "考试不存在");
        }
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(exam.getChapter());
        if (chapter == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        ServerResponse permissionResult = permissionService.checkCourseMemberPermission(userId,chapter.getCourse());
        if (!permissionResult.isSuccess()){
            return permissionResult;
        }
        exam = (ChapterExam) examService.shuffleQuestionList(exam);
        return ServerResponse.createBySuccess("查询成功", exam);
    }

    @Override
    public ServerResponse submit(AnswerComplex answer) {
        ChapterExam exam = chapterExamMapper.findExamByPrimaryKey(answer.getExam());
        if (exam == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "考试不存在");
        }
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(exam.getChapter());
        if (chapter == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(chapter.getCourse(), answer.getUser());
        if (study == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "未参加学习");
        }
        //计算得分
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(), exam.getId());
        int newScore = examService.countScore(answer);
        if (score == null){
            //第一次提交创建记录
            score = new Score();
            score.setStudy(study.getId());
            score.setExam(exam.getId());
            score.setScore(newScore);
            return scoreService.insert(score);
        }
        if (score.getScore() > newScore) {
            //低于最高分不更新记录
            return ServerResponse.createBySuccess("提交成功", newScore);
        }
        //高于最高分更新记录
        score.setScore(newScore);
        return scoreService.update(score);
    }

    @Override
    public ServerResponse score(String userId, String chapterId) {
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
        if (chapter == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(chapter.getCourse(), userId);
        if (study == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "未参加学习");
        }
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(), chapter.getChapterExam());
        if (score == null) {
            return ServerResponse.createByError("暂无成绩");
        }
        return ServerResponse.createBySuccess("查询成功", score);
    }

    @Override
    public void associateDelete(String examId) {
        //记录查询
        ChapterExam exam = chapterExamMapper.findExamByPrimaryKey(examId);
        if (exam == null) {
            log.warn("考试记录获取失败");
            return;
        }
        //级联删除
        questionService.associateDelete(exam.getId());
        //删除记录
        log.info("删除考试:" + exam.toString());
        if (chapterExamMapper.delete(exam.getId()) == 0) {
            log.warn("考试记录删除失败");
        }
    }
}
