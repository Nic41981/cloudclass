package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.CourseSpinner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface CourseMapper {
    /**
     * 李九龙
     */
    String findTeacherIdByPrimaryKey(@Param("id") String id);

    void add(Course course);
    void modify(Course course);
    int selectCourseId(@Param("id") String id);
    void deleteCourseById(String id);
    Course findCourseById(@Param("id") String id);

    List<Course> getCourses(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize, @Param("teacher") String teacher);
    List<CourseSpinner> getCourseSpinnerList();
}
