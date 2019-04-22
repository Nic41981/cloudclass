package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.domain.StudentScore;
import edu.qit.cloudclass.domain.complex.ScoreWithName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface ScoreMapper {
    int insert(@Param("score") Score score);

    int delete(@Param("id")int id);

    int update(@Param("score") Score score);

    List<Score> selectScoreListByStudy(@Param("study")int study);

    Score findScoreByStudyAndExam(@Param("study")int study,@Param("exam")String exam);


    List<ScoreWithName> selectScoreWithNameListByExam(@Param("exam")String exam);
}
