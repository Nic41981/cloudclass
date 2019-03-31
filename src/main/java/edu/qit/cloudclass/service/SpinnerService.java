package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.ChapterSpinner;
import edu.qit.cloudclass.domain.CourseSpinner;
import edu.qit.cloudclass.domain.ExamSpinner;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

/**
 * Created by Root
 */

public interface SpinnerService {

    ServerResponse<List<CourseSpinner>> getCourseList();
    ServerResponse<List<ChapterSpinner>> getChapterList(String courseId);
    ServerResponse<List<ExamSpinner>> getExaminationList(String courseId);

}
