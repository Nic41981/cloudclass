package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.tool.ServerResponse;

import java.util.List;

public interface CourseService {

    ServerResponse<List<Course>> TagList(String tag);

}