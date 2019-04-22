package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.domain.StudentScore;
import edu.qit.cloudclass.domain.ScorePercent;
import edu.qit.cloudclass.domain.complex.ScoreWithName;
import edu.qit.cloudclass.service.TStatisticsService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Root
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TStatisticsServiceImpl implements TStatisticsService {

    private final ScoreMapper scoreMapper;
    private final StudyMapper studyMapper;

    @Override
    public ServerResponse studentStatistics(String courseId) {
        List<String> nameList = studyMapper.selectUserNameListByCourse(courseId);
        int total = nameList.size();
        if (total == 0){
            return ServerResponse.createBySuccessMsg("无人参加该课程");
        }
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("total",nameList.size());
        result.put("nameList",nameList);
        return ServerResponse.createBySuccess("查询成功", result);
    }

    @Override
    public ServerResponse scoreStatistics(String examId) {
        List<ScoreWithName> scoreList = scoreMapper.selectScoreWithNameListByExam(examId);
        int total = scoreList.size();
        if (total == 0){
            return ServerResponse.createBySuccessMsg("无人参加该测试");
        }
        int perfect = 0,excellent = 0,good = 0,pass = 0,fail = 0;
        for (ScoreWithName scoreItem : scoreList){
            int score = scoreItem.getScore();
            if (score > 90){
                perfect ++;
            }
            else if (score >= 80){
                excellent ++;
            }
            else if (score >= 70){
                good ++;
            }
            else if (score >= 60){
                pass ++;
            }
            else{
                fail ++;
            }
        }
        int passNum = total - fail;
        double passNumPercent = (double)passNum / total * 100;
        double perfectPercent = (double)perfect / total * 100;
        double excellentPercent = (double)excellent / total * 100;
        double goodPercent = (double)good / total * 100;
        double passPercent = (double)pass / total * 100;
        double failPercent = (double)fail / total * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("total",total);
        result.put("passNum",passNum);
        result.put("passNumPercent",df.format(passNumPercent));
        result.put("scoreList",scoreList);
        result.put("perfectPercent",df.format(perfectPercent));
        result.put("excellentPercent",df.format(excellentPercent));
        result.put("goodPercent",df.format(goodPercent));
        result.put("passPercent",df.format(passPercent));
        result.put("failPercent",df.format(failPercent));
        return ServerResponse.createBySuccess("查询成功",result);
    }
}
