package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ScoreMapper;
import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScoreServiceImpl implements ScoreService {
    private final ScoreMapper scoreMapper;

    @Override
    public void associateDelete(int scoreId) {
        Score score = scoreMapper.findScoreByPrimaryKey(scoreId);
        log.info("删除成绩:" + score.toString());
        if (scoreMapper.delete(scoreId) == 0){
            log.warn("成绩记录删除失败");
        }
    }
}
