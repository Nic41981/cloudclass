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
    int insert(@Param("course") Course course);

    int delete(@Param("id") String id);

    int modify(@Param("course") Course course);

    List<Course> coursesList(@Param("teacher") String teacher);

    Course findCourseByPrimaryKey(@Param("id") String id);

    /**
     * 王恺
     */
    List<CourseSpinner> getCourseSpinnerList();

    /**
     * 董悦
     */
    int checkCourseExist(@Param("id") String id);

    /**
     * 董悦
     */
    String findTeacherIdByPrimaryKey(@Param("id")String id);

    /**
     * 董悦
     */
    int updateImageIdAfterUpdate(@Param("id") String id,@Param("image") String image);
}
