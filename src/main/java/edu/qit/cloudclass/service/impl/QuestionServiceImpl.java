package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.QuestionMapper;
import edu.qit.cloudclass.domain.Answer;
import edu.qit.cloudclass.domain.Question;
import edu.qit.cloudclass.service.QuestionService;
import edu.qit.cloudclass.tool.ServerResponse;
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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    @Override
    public void insertList(List<Question> questions,String examId,String type) {
        log.info("创建问题:");
        for (Question question : questions){
            question.setExam(examId);
            question.setType(type);
            question.encodeOption();
            log.info(question.toString());
        }
        if (questionMapper.insertList(questions) != questions.size()){
            log.warn("问题列表没有完整创建");
        }
    }

    @Override
    public List<Question> getQuestionListByType(String examId, String type) {
        List<Question> questionList = questionMapper.selectQuestionListByExamAndType(examId,type);
        for (Question question : questionList){
            question.decodeOption();
        }
        return questionList;
    }

    @Override
    public List<Answer> getAnswerListByType(String examId, String type) {
        return questionMapper.selectAnswerListByExamAndType(examId,type);
    }

    @Override
    public void associateDelete(String examId) {
        List<Question> questionList = questionMapper.questionList(examId);
        if (questionList == null){
            log.warn("题目列表获取失败");
            return;
        }
        for (Question question : questionList){
            log.info("删除题目:" + question.toString());
            if (questionMapper.delete(question.getId()) == 0){
                log.warn("题目记录删除失败");
            }
        }
    }
}
