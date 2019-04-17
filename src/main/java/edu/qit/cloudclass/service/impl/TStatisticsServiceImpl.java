package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
import edu.qit.cloudclass.domain.ScoreItem;
import edu.qit.cloudclass.domain.ScorePercent;
import edu.qit.cloudclass.domain.StudyItem;
import edu.qit.cloudclass.domain.spinner.ChapterSpinner;
import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.service.TStatisticsService;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * Created by Root
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TStatisticsServiceImpl implements TStatisticsService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final ScoreMapper scoreMapper;
    private final StudyMapper studyMapper;

    @Override
    public ServerResponse<List<CourseSpinner>> getCourseList() {
        List<CourseSpinner> courseList = courseMapper.getCourseSpinnerList();
        return ServerResponse.createBySuccess("查询成功",courseList);
    }

    @Override
    public ServerResponse getChapterList(String courseId) {
        ServerResponse existResult = checkCourseExist(courseId);
        if (!existResult.isSuccess()) {
            return existResult;
        }
        List<ChapterSpinner> courseList = chapterMapper.getChapterSpinnerList(courseId);
        return ServerResponse.createBySuccess("查询成功", courseList);
    }

    @Override
    public ServerResponse getScoreList(String examId) {
        List<ScoreItem> scoreItems = scoreMapper.getScoreList(examId);
        return ServerResponse.createBySuccess("查询成功", scoreItems);
    }

    @Override
    public ServerResponse getStudyList(String courseId) {
        List<StudyItem> studyItems = studyMapper.findStudyByCourseId(courseId);
        return ServerResponse.createBySuccess("查询成功", studyItems);
    }

    @Override
    public ServerResponse checkCourseExist(String courseId) {
        int flag = courseMapper.checkCourseExist(courseId);
        if (flag == 0){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse getScorePercent(String examId) {
        List<ScoreItem> scoreItems = scoreMapper.getScoreList(examId);
        int totalCount = scoreItems.size();
        ScorePercent scorePercent = new ScorePercent();
        double A = 0;
        double B = 0;
        double C = 0;
        double D = 0;
        double E = 0;
        if (totalCount <= 0 ){
            return ServerResponse.createByError(-1, "查询失败");
        }
        for (int i=0;i<scoreItems.size();i++){
            if (scoreItems.get(i).getScore()>=90){
                A++;
            } else if (scoreItems.get(i).getScore()<90&&scoreItems.get(i).getScore()>=80){
                B++;
            } else if(scoreItems.get(i).getScore()<80&&scoreItems.get(i).getScore()>=70){
                C++;
            }else if(scoreItems.get(i).getScore()<70&&scoreItems.get(i).getScore()>=60){
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
