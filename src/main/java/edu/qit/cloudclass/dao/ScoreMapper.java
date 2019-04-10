package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Score;
import org.apache.ibatis.annotations.Param;

/**
 * @author nic
 * @version 1.0
 */
public interface ScoreMapper {
    int insert(@Param("score") Score score);
    int delete(@Param("id")int id);
    Score findScoreByPrimaryKey(@Param("id")int id);
    Score findScoreByStudyAndExam(@Param("study")int study,@Param("exam")String exam);
}
