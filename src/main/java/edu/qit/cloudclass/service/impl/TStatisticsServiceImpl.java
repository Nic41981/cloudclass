package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
import edu.qit.cloudclass.domain.Score;
import edu.qit.cloudclass.domain.StudentScore;
import edu.qit.cloudclass.domain.ScorePercent;
import edu.qit.cloudclass.service.TStatisticsService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Map<String ,Object> result = new LinkedHashMap<>();
        result.put("total",nameList.size());
        result.put("nameList",nameList);
        return ServerResponse.createBySuccess("查询成功", result);
    }

    @Override
    public ServerResponse scoreStatistics(String examId) {
        List<Score> scoreList = scoreMapper.selectScoreListByExam(examId);
        List<StudentScore> studentScores = scoreMapper.getScoreList(examId);
        return ServerResponse.createBySuccess("查询成功", studentScores);
    }


    @Override
    public ServerResponse getScorePercent(String examId) {
        List<StudentScore> studentScores = scoreMapper.getScoreList(examId);
        int totalCount = studentScores.size();
        ScorePercent scorePercent = new ScorePercent();
        double A = 0;
        double B = 0;
        double C = 0;
        double D = 0;
        double E = 0;
        if (totalCount <= 0 ){
            return ServerResponse.createByError(-1, "查询失败");
        }
        for (int i = 0; i< studentScores.size(); i++){
            if (studentScores.get(i).getScore()>=90){
                A++;
            } else if (studentScores.get(i).getScore()<90&& studentScores.get(i).getScore()>=80){
                B++;
            } else if(studentScores.get(i).getScore()<80&& studentScores.get(i).getScore()>=70){
                C++;
            }else if(studentScores.get(i).getScore()<70&& studentScores.get(i).getScore()>=60){
                D++;
            }else{
                E++;
            }
        }
            scorePercent.setAPrecent(Double.toString(A/totalCount));
            scorePercent.setBPrecent(Double.toString(B/totalCount));
            scorePercent.setCPrecent(Double.toString(C/totalCount));
            scorePercent.setDPrecent(Double.toString(D/totalCount));
            scorePercent.setEPrecent(Double.toString(E/totalCount));

        return ServerResponse.createBySuccess("查询成功",scorePercent);
    }

}
