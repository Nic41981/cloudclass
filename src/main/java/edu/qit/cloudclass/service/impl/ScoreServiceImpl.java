package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ScoreMapper;
import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.service.ScoreService;
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
public class ScoreServiceImpl implements ScoreService {
    private final ScoreMapper scoreMapper;

    @Override
    public void associateDelete(int studyId) {
        List<Score> scoreList = scoreMapper.selectScoreListByStudy(studyId);
        if (scoreList == null){
            log.warn("获取成绩列表失败");
            return;
        }
        for (Score score : scoreList){
            log.info("删除成绩:" + score);
            if (scoreMapper.delete(score.getId()) == 0){
                log.warn("删除成绩记录失败");
            }
        }
    }

    @Override
    public ServerResponse insert(Score score) {
        log.info("创建成绩:" + score);
        if (scoreMapper.insert(score) == 0) {
            return ServerResponse.createByError("提交失败");
        }
        return ServerResponse.createBySuccess("提交成功", score);
    }

    @Override
    public ServerResponse update(Score score) {
        log.info("更新成绩:" + score);
        if (scoreMapper.update(score) == 0){
            return ServerResponse.createByError("提交失败");
        }
        return ServerResponse.createBySuccess("提交成功",score);
    }

    @Override
    public Score findScoreByStudyAndExam(int study, String exam) {
        return scoreMapper.findScoreByStudyAndExam(study,exam);
    }
}
