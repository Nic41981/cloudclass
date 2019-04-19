package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

public interface TCourseService {
    ServerResponse add(Course course);

    ServerResponse modify(Course course);

    ServerResponse deleteCourseById(String id);

    ServerResponse<List<Course>> getCourses(String teacher);
}
