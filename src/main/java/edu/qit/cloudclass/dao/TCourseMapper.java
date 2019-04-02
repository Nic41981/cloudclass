package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Mapper
public interface TCourseMapper {
     void add(Course course);
     int modify(Course course);
     void deleteCourseById(String id);
     Course findCourseById(@Param("id") String id);
     List<Course> getCourses(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize);
}
