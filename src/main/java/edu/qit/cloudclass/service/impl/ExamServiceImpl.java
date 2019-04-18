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
    public int countScore(AnswerComplex answer) {
        int total = 0;
        log.info("==========选择结算开始==========");
        int score = 0;
        List<Answer> tAnswerList = questionService.getAnswerListByType(answer.getExam(), Question.CHOICE_QUESTION);
        List<Answer> sAnswerList = answer.getChoiceList();
        for (Answer sAnswer : sAnswerList) {
            if (tAnswerList.contains(sAnswer)) {
                int index = tAnswerList.indexOf(sAnswer);
                Answer tAnswer = tAnswerList.get(index);
                log.info("题目ID:" + sAnswer.getId() + ",总分:" + tAnswer.getScore() + ",标准答案:" + tAnswer.getAnswer() + ",学生答案:" + sAnswer.getAnswer());
                if (tAnswer.getAnswer().equals(sAnswer.getAnswer())) {
                    score += tAnswer.getScore();
                }
            }
        }
        log.info("选择总得分:" + score);
        total += score;
        log.info("==========选择结算结束==========");
        log.info("==========判断结算开始==========");
        score = 0;
        tAnswerList = questionService.getAnswerListByType(answer.getExam(), Question.JUDGEMENT_QUESTION);
        sAnswerList = answer.getJudgementList();
        for (Answer sAnswer : sAnswerList) {
            if (tAnswerList.contains(sAnswer)) {
                int index = tAnswerList.indexOf(sAnswer);
                Answer tAnswer = tAnswerList.get(index);
                log.info("题目ID:" + sAnswer.getId() + ",总分:" + tAnswer.getScore() + ",标准答案:" + tAnswer.getAnswer() + ",学生答案:" + sAnswer.getAnswer());
                if (tAnswer.getAnswer().equals(sAnswer.getAnswer())) {
                    score += tAnswer.getScore();
                }
            }
        }
        log.info("判断总得分:" + score);
        total += score;
        log.info("==========判断结算结束==========");
        return total;
    }


    @Override
    public List<Question> parserQuestion(List<Question> questionList, String examId, String type) {
        if (questionList == null) {
            return null;
        }
        for (Question question : questionList) {
            question.setExam(examId);
            question.setType(type);
            switch (type) {
                case Question.CHOICE_QUESTION: {
                    if (!question.isChoiceQuestion()) {
                        log.error("选择题解析错误:" + question);
                        questionList.remove(question);
                    } else {
                        question.encodeOption();
                    }
                    break;
                }
                case Question.JUDGEMENT_QUESTION: {
                    if (!question.isJudgementQuestion()) {
                        log.error("判断题解析错误:" + question);
                        questionList.remove(question);
                    }
                    break;
                }
                default: {
                    return null;
                }
            }
        }
        return questionList;
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

}
