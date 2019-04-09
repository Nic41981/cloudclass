package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.ExamMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Exam;
import edu.qit.cloudclass.domain.Question;
import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.service.ExaminationService;
import edu.qit.cloudclass.service.QuestionService;
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
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaminationServiceImpl implements ExaminationService {

    private final ExamMapper examMapper;
    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final QuestionService questionService;

    @Override
    public ServerResponse createFinalExam(ExamComplex exam, String courseId) {
        //合理性判断
        ServerResponse checkResult = examCheck(exam);
        if (!checkResult.isSuccess()){
            return checkResult;
        }
        log.info("==========创建期末考试开始==========");
        //查询旧的期末考试号
        Course course = courseMapper.findCourseByPrimaryKey(courseId);
        if (course == null){
            log.error("==========查询课程信息失败==========");
            return ServerResponse.createByError("创建失败");
        }
        //级联删除
        if (course.getFinalExam() != null){
            associateDelete(course.getFinalExam());
        }
        //创建期末考试
        exam.setId(Tool.uuid());
        log.info("创建期末考试:" + exam.toString());
        if (courseMapper.updateFinalExamAfterUpload(courseId,exam.getId()) == 0){
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
    public ServerResponse createChapterExam(ExamComplex exam, String chapterId) {
        //合理性判断
        ServerResponse checkResult = examCheck(exam);
        if (!checkResult.isSuccess()){
            return checkResult;
        }
        log.info("==========创建章节测试开始==========");
        //查询旧的章节测试号
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
        if (chapter == null){
            log.error("==========查询章节信息失败==========");
            return ServerResponse.createByError("创建失败");
        }
        //级联删除
        if(chapter.getChapterExam() != null){
            associateDelete(chapter.getChapterExam());
        }
        //创建期末考试
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
        ExamComplex exam = ExamComplex.getInstanceFromEXam(examMapper.findExamByPrimaryKey(examId));
        if (exam == null){
            return ServerResponse.createByError("查询失败");
        }
        exam.setChoiceList(questionService.getQuestionListByType(exam.getId(),Question.CHOICE_QUESTION));
        exam.setJudgementList(questionService.getQuestionListByType(exam.getId(),Question.JUDGEMENT_QUESTION));
        return ServerResponse.createBySuccess("查询成功",exam);
    }

    @Override
    public ServerResponse examCheck(ExamComplex exam) {
        List<Question> choiceList = exam.getChoiceList();
        List<Question> judgementList = exam.getJudgementList();
        if (choiceList.isEmpty() || judgementList.isEmpty()){
            return ServerResponse.createByError("习题过少");
        }
        if (exam.getStopTime().getTime() - exam.getStartTime().getTime() < 24 * 60 * 60 * 1000){
            return ServerResponse.createByError("考试时间过短");
        }
        if (exam.getDuration() < 5){
            return ServerResponse.createByError("考试时长过短");
        }
        if (exam.getStopTime().getTime() - exam.getStartTime().getTime() < exam.getDuration() * 60 * 1000){
            return ServerResponse.createByError("考试时长过长");
        }
        return ServerResponse.createBySuccess();
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
}
