package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.TCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Mapper
public interface TCourseMapper {
     void add(TCourse tCourse);
     int modify(TCourse tCourse);
     void deleteCourseById(String id);
     TCourse findCourseById(@Param("id") String id);
     List<TCourse> getCourses(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize);
}
