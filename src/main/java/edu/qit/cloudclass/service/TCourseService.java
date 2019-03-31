package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.TCourse;
import edu.qit.cloudclass.tool.ServerResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TCourseService {
    ServerResponse add(TCourse tCourse);
    ServerResponse modify(TCourse tCourse);
    ServerResponse deleteCourseById(String id);
    ServerResponse<TCourse> findCourseById(@Param("id") String id);
    ServerResponse<List<TCourse>> getCourses(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize);
}
