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
    private final StudyMapper studyMapper;
    private final ScoreMapper scoreMapper;
    private final QuestionService questionService;
    private final ExamService examService;
    private final ScoreService scoreService;

    @Override
    public ServerResponse create(ChapterExam exam, String userId) {
        exam.setId(Tool.uuid());
        //查询课程信息
        Chapter chapter = chapterMapper.findChapterByPrimaryKey(exam.getChapter());
        //题目信息处理
        exam = (ChapterExam) examService.parserQuestion(exam);
        if (exam.getChoiceList().isEmpty() || exam.getJudgementList().isEmpty()){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "有效问题不足");
        }
        exam = (ChapterExam) examService.parserQuestionScore(exam);
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
        log.info("有效选择题数目:" + exam.getChoiceList().size());
        questionService.insertList(exam.getChoiceList());
        log.info("有效判断题数目:" + exam.getJudgementList().size());
        questionService.insertList(exam.getJudgementList());
        log.info("==========创建章节测试结束==========");
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse page(String examId) {
        ChapterExam exam = chapterExamMapper.findExamByPrimaryKey(examId);
        exam = (ChapterExam) examService.shuffleQuestionList(exam);
        return ServerResponse.createBySuccess("查询成功", exam);
    }

    @Override
    public ServerResponse submit(String userId,String examId,AnswerComplex answer) {
        String courseId = chapterMapper.findCourseIdByChapterExam(examId);
        Study study = studyMapper.findStudyByCourseAndStudent(courseId, userId);
        //计算得分
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(),examId);
        int newScore = examService.countScore(answer,examId);
        if (score == null){
            //第一次提交创建记录
            score = new Score();
            score.setStudy(study.getId());
            score.setExam(examId);
            score.setScore(newScore);
            return scoreService.insert(score);
        }
        if (score.getScore() > newScore) {
            //低于最高分不更新记录
            score.setScore(newScore);
            return ServerResponse.createBySuccess("提交成功", score);
        }
        //高于最高分更新记录
        score.setScore(newScore);
        return scoreService.update(score);
    }

    @Override
    public ServerResponse score(String userId, String examId) {
        String courseId = chapterMapper.findCourseIdByChapterExam(examId);
        Study study = studyMapper.findStudyByCourseAndStudent(courseId, userId);
        Score score = scoreMapper.findScoreByStudyAndExam(study.getId(), examId);
        if (score == null) {
            return ServerResponse.createBySuccessMsg("暂无成绩");
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
