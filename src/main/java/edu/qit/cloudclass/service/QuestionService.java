package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Answer;
import edu.qit.cloudclass.domain.Question;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
public interface QuestionService {
    void insertList(List<Question> questions,String examId,String type);
    List<Question> getQuestionListByType(String examId,String type);
    List<Answer> getAnswerListByType(String examId, String type);
    void associateDelete(String examId);
}
