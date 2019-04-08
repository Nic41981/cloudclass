package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

/**
 * Created by Root
 */

public interface SpinnerService {

    ServerResponse<List<CourseSpinner>> getCourseList();
    ServerResponse getChapterList(String courseId);
    ServerResponse getExaminationList(String courseId);
    ServerResponse checkCourseExist(String courseId);
}
