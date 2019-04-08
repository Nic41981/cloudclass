package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.spinner.SCourseSpinner;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

public interface SCourseService {
    ServerResponse<List<SCourseSpinner>> scourseList(String studentId);
    ServerResponse insertScourse(String courseId,String studentId);
    ServerResponse deleteScourse(String courseId,String studentId);
}
