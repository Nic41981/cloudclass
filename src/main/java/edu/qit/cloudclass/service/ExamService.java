package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.AbstractExam;
import edu.qit.cloudclass.domain.Question;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
public interface ExamService {
    int countScore(AnswerComplex answer,String examId);

    List<Question> parserQuestion(List<Question> questionList, String examId, String type);

    AbstractExam shuffleQuestionList(AbstractExam exam);

    AbstractExam parserQuestion(AbstractExam exam);
}
