package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.domain.*;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.service.ExamService;
import edu.qit.cloudclass.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamServiceImpl implements ExamService {
    private final QuestionService questionService;

    @Override
    public int countScore(AnswerComplex answer,String examId) {
        int total = 100;
        log.info("==========选择结算开始==========");
        List<Answer> tAnswerList = questionService.getAnswerListByType(examId, Question.CHOICE_QUESTION);
        List<Answer> sAnswerList = answer.getChoiceList();
        for (Answer tAnswer : tAnswerList){
            log.info("题目ID:" + tAnswer.getId());
            log.info("标准答案:" + tAnswer.getAnswer());
            if (sAnswerList.contains(tAnswer)){
                int index = sAnswerList.indexOf(tAnswer);
                Answer sAnswer = sAnswerList.get(index);
                log.info("学生答案:" + sAnswer.getAnswer());
                if (!tAnswer.getAnswer().equals(sAnswer.getAnswer())){
                    total -= tAnswer.getScore();
                }
                else {
                    log.info("得分:" + tAnswer.getScore());
                }
            }
            else {
                log.info("学生未作答");
                total -= tAnswer.getScore();
            }
        }
        log.info("==========选择结算结束==========");
        log.info("==========判断结算开始==========");
        tAnswerList = questionService.getAnswerListByType(examId, Question.JUDGEMENT_QUESTION);
        sAnswerList = answer.getJudgementList();
        for (Answer tAnswer : tAnswerList){
            log.info("题目ID:" + tAnswer.getId());
            log.info("标准答案:" + tAnswer.getAnswer());
            if (sAnswerList.contains(tAnswer)){
                int index = sAnswerList.indexOf(tAnswer);
                Answer sAnswer = sAnswerList.get(index);
                log.info("学生答案:" + sAnswer.getAnswer());
                if (!tAnswer.getAnswer().equals(sAnswer.getAnswer())){
                    total -= tAnswer.getScore();
                }
                else {
                    log.info("得分:" + tAnswer.getScore());
                }
            }
            else {
                log.info("学生未作答");
                total -= tAnswer.getScore();
            }
        }
        log.info("==========判断结算结束==========");
        return total;
    }

    @Override
    public AbstractExam shuffleQuestionList(AbstractExam exam) {
        List<Question> choiceList = questionService.getQuestionListByType(exam.getId(), Question.CHOICE_QUESTION);
        choiceList = new ArrayList<>(choiceList);
        Collections.shuffle(choiceList);
        exam.setChoiceList(choiceList);
        List<Question> judgementList = questionService.getQuestionListByType(exam.getId(), Question.JUDGEMENT_QUESTION);
        judgementList = new ArrayList<>(judgementList);
        Collections.shuffle(judgementList);
        exam.setJudgementList(judgementList);
        return exam;
    }

    @Override
    public AbstractExam parserQuestion(AbstractExam exam) {
        List<Question> questionList = exam.getChoiceList();
        if (questionList != null) {
            for (Question question : questionList) {
                question.setExam(exam.getId());
                question.setType(Question.CHOICE_QUESTION);
                if (!question.isChoiceQuestion()) {
                    log.warn("选择题解析错误:" + question);
                    questionList.remove(question);
                } else {
                    question.encodeOption();
                }
            }
        }
        exam.setChoiceList(questionList);
        questionList = exam.getJudgementList();
        if (questionList != null) {
            for (Question question : questionList) {
                question.setExam(exam.getId());
                question.setType(Question.JUDGEMENT_QUESTION);
                if (!question.isJudgementQuestion()) {
                    log.warn("判断题解析错误:" + question);
                    questionList.remove(question);
                }
            }
        }
        exam.setJudgementList(questionList);
        return exam;
    }

    @Override
    public AbstractExam parserQuestionScore(AbstractExam exam){
        int totalWeight = exam.getChoiceScoreWeight() + exam.getJudgementScoreWeight();
        double totalChoiceScore = 100.0 * exam.getChoiceScoreWeight() / totalWeight;
        log.info("选择题总分:" + totalChoiceScore);
        int choiceScore = (int)(totalChoiceScore / exam.getChoiceList().size());
        log.info("选择题分值:" + choiceScore);
        List<Question> questionList = exam.getChoiceList();
        for (Question question : questionList){
            question.setScore(choiceScore);
        }
        exam.setChoiceList(questionList);
        double totalJudgementScore = 100.0 * exam.getJudgementScoreWeight() / totalWeight;
        log.info("判断题总分:" + totalJudgementScore);
        int judgementScore = (int) (totalJudgementScore / exam.getJudgementList().size());
        log.info("判断题分值:" + judgementScore);
        questionList = exam.getJudgementList();
        for (Question question : questionList){
            question.setScore(judgementScore);
        }
        exam.setJudgementList(questionList);
        return exam;
    }

}
