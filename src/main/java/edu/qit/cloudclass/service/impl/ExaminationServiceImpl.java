package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
import edu.qit.cloudclass.domain.*;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.service.ExaminationService;
import edu.qit.cloudclass.service.QuestionService;
import edu.qit.cloudclass.service.ScoreService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaminationServiceImpl implements ExaminationService {

    private final ExamMapper examMapper;
    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final StudyMapper studyMapper;
    private final ScoreMapper scoreMapper;
    private final QuestionService questionService;
    private final ScoreService scoreService;

    @Override
    public ServerResponse createFinalExam(ExamComplex exam,String userId) {
        //查询课程信息
        Course course = courseMapper.findCourseByPrimaryKey(exam.getCourse());
        if (course == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!course.getTeacher().equals(userId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        //级联删除
        if (course.getFinalExam() != null){
            associateDelete(course.getFinalExam());
        }
        //创建期末考试
        log.info("==========创建期末考试开始==========");
        exam.setId(Tool.uuid());
        log.info("创建期末考试:" + exam.toString());
        if (courseMapper.updateFinalExamAfterUpload(course.getId(),exam.getId()) == 0){
            log.error("==========考试信息更新失败==========");
            return ServerResponse.createByError("创建失败");
        }
        if (examMapper.insert(exam) == 0){
            log.error("==========试卷信息创建失败==========");
            return ServerResponse.createByError("创建失败");
        }
        questionService.insertList(exam.getChoiceList(),exam.getId(),Question.CHOICE_QUESTION);
        questionService.insertList(exam.getJudgementList(),exam.getId(),Question.JUDGEMENT_QUESTION);
        log.info("==========创建期末考试结束==========");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse createChapterExam(ExamComplex exam, String chapterId, String userId) {
        //查询课程信息
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
        if (chapter == null || !chapter.getCourse().equals(exam.getCourse())){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        Course course = courseMapper.findCourseByPrimaryKey(chapter.getCourse());
        if (course == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!course.getTeacher().equals(userId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"权限不足");
        }
        //级联删除
        if(chapter.getChapterExam() != null){
            associateDelete(chapter.getChapterExam());
        }
        //创建章节测试
        log.info("==========创建章节测试开始==========");
        exam.setId(Tool.uuid());
        log.info("创建章节测试:" + exam.toString());
        if (chapterMapper.updateChapterExamAfterUpload(chapter.getId(),exam.getId()) == 0){
            log.error("==========考试信息更新失败==========");
            return ServerResponse.createByError("创建失败");
        }
        if (examMapper.insert(exam) == 0){
            log.error("==========试卷信息创建失败==========");
            return ServerResponse.createByError("创建失败");
        }
        questionService.insertList(exam.getChoiceList(),exam.getId(),Question.CHOICE_QUESTION);
        questionService.insertList(exam.getJudgementList(),exam.getId(),Question.JUDGEMENT_QUESTION);
        log.info("==========创建章节测试结束==========");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse getExamPage(String examId) {
        Exam exam = examMapper.findExamByPrimaryKey(examId);
        if (exam == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"考试不存在");
        }
        ExamComplex examComplex = ExamComplex.getInstanceFromExam(exam);
        List<Question> choiceList = questionService.getQuestionListByType(examComplex.getId(),Question.CHOICE_QUESTION);
        Collections.shuffle(choiceList);
        examComplex.setChoiceList(choiceList);
        List<Question> judgementList = questionService.getQuestionListByType(examComplex.getId(),Question.JUDGEMENT_QUESTION);
        Collections.shuffle(judgementList);
        examComplex.setJudgementList(judgementList);
        return ServerResponse.createBySuccess("查询成功",examComplex);
    }

    @Override
    public ServerResponse submitExam(AnswerComplex answer) {
        Exam exam = examMapper.findExamByPrimaryKey(answer.getExam());
        if (exam == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"考试不存在");
        }
        if (!exam.isSubmittable()){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不在考试时间");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(exam.getCourse(),answer.getUser());
        if (study == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "权限不足");
        }
        //计算得分
        log.info("==========得分结算==========");
        List<Answer> tChoiceList = questionService.getAnswerListByType(answer.getExam(),Question.CHOICE_QUESTION);
        List<Answer> sChoiceList = answer.getChoiceList();
        int choiceScore = countScore(tChoiceList,sChoiceList);
        log.info("选择题总得分:" + choiceScore);
        List<Answer> tJudgementList = questionService.getAnswerListByType(answer.getExam(),Question.JUDGEMENT_QUESTION);
        List<Answer> sJudgementList = answer.getJudgementList();
        int judgementScore = countScore(tJudgementList,sJudgementList);
        log.info("判断题总得分:" + judgementScore);
        log.info("==========结算结束==========");
        //级联删除记录
        Score oldScore = scoreMapper.findScoreByStudyAndExam(study.getId(),exam.getId());
        if (oldScore != null){
            scoreService.associateDelete(oldScore.getId());
        }
        //创建记录
        Score score = new Score();
        score.setStudy(study.getId());
        score.setExam(answer.getExam());
        score.setScore(choiceScore + judgementScore);
        log.info("插入成绩:" + score);
        if (scoreMapper.insert(score) == 0){
            return ServerResponse.createByError("提交失败");
        }
        return ServerResponse.createBySuccess("提交成功",score);
    }

    @Override
    public ServerResponse finalExamScore(String userId, String courseId) {
        Course course = courseMapper.findCourseByPrimaryKey(courseId);
        if (course == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (course.getFinalExam() == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"考试不存在");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(courseId,userId);
        if (study == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"未参加学习");
        }
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(),course.getFinalExam());
        if (score == null){
            return ServerResponse.createByError("暂无成绩");
        }
        return ServerResponse.createBySuccess("查询成功",score);
    }

    @Override
    public ServerResponse chapterExamScore(String userId, String chapterId) {
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
        if (chapter == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        if (chapter.getChapterExam() == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"考试不存在");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(chapter.getCourse(),userId);
        if (study == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"未参加学习");
        }
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(),chapter.getChapterExam());
        if (score == null){
            return ServerResponse.createByError("暂无成绩");
        }
        return ServerResponse.createBySuccess("查询成功",score);
    }

    @Override
    public ServerResponse associateDelete(String examId) {
        //记录查询
        Exam exam = examMapper.findExamByPrimaryKey(examId);
        if (exam == null){
            log.warn("考试记录获取失败");
            return ServerResponse.createBySuccess();
        }
        //级联删除
        questionService.associateDelete(exam.getId());
        //删除记录
        log.info("删除考试:" + exam.toString());
        if (examMapper.delete(exam.getId()) == 0){
            log.warn("考试记录删除失败");
        }
        return null;
    }

    private int countScore(List<Answer> tAnswerList,List<Answer> sAnswerList){
        int score = 0;
        for (Answer sAnswer : sAnswerList){
            if (tAnswerList.contains(sAnswer)){
                int index = tAnswerList.indexOf(sAnswer);
                Answer tAnswer = tAnswerList.get(index);
                log.info("题目ID:" + sAnswer.getId() + ",总分:" + tAnswer.getScore() + ",标准答案:" + tAnswer.getAnswer() + ",学生答案:" + sAnswer.getAnswer());
                if (tAnswer.getAnswer().equals(sAnswer.getAnswer())){
                    score += tAnswer.getScore();
                }
            }
        }
        return score;
    }
}
