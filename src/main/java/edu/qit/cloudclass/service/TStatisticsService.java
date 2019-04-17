package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.ScoreItem;
import edu.qit.cloudclass.domain.StudyItem;
import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

/**
 * Created by Root
 */
public interface TStatisticsService {

    ServerResponse<List<CourseSpinner>> getCourseList();
    ServerResponse getChapterList(String courseId);
    ServerResponse<List<ScoreItem>> getScoreList(String examId);
    ServerResponse<List<StudyItem>> getStudyList(String courseId);
    ServerResponse checkCourseExist(String courseId);
    ServerResponse getScorePercent(String examId);

}
