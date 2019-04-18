package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.domain.ScoreItem;
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

    List<Score> selectScoreListByStudy(@Param("study")int study);

    Score findScoreByPrimaryKey(@Param("id")int id);

    Score findScoreByStudyAndExam(@Param("study")int study,@Param("exam")String exam);

    List<ScoreItem> getScoreList(@Param("exam")String exam);

    int update(Score score);
}
