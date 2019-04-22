package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.spinner.SCourseSpinner;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

public interface SCourseService {
    ServerResponse courseList(String studentId);

    ServerResponse courseSpinnerList(String userId);

    ServerResponse joinCourse(String courseId, String studentId);

    ServerResponse exitCourse(String courseId, String studentId);
}
