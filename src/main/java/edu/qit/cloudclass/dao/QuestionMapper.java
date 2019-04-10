package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Answer;
import edu.qit.cloudclass.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface QuestionMapper {

    List<Question> selectQuestionListByExamAndType(@Param("exam")String exam,@Param("type")String type);

    List<Answer> selectAnswerListByExamAndType(@Param("exam")String exam,@Param("type")String type);

    int insertList(@Param("questions")List<Question> questions);

    int delete(@Param("id") int id);

    List<Question> questionList(@Param("exam") String exam);
}
