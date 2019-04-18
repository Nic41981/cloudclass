package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface ScoreService {

    ServerResponse insert(Score score);

    ServerResponse update(Score score);

    Score findScoreByStudyAndExam(int study, String exam);

    void associateDelete(int scoreId);
}
