package edu.qit.cloudclass.service;

import edu.qit.cloudclass.tool.ServerResponse;

/**
 * Created by Root
 */
public interface TStatisticsService {
    ServerResponse studentStatistics(String courseId);
    ServerResponse scoreStatistics(String examId);
    ServerResponse getScorePercent(String examId);

}
