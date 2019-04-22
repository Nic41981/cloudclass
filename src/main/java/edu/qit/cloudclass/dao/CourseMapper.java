package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.domain.spinner.SCourseSpinner;
import javafx.scene.control.Spinner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {

    int insert(@Param("course") Course course);

    int delete(@Param("id") String id);

    int modify(@Param("course") Course course);

    List<Course> selectCoursesListByTeacher(@Param("teacher") String teacher);

    Course findCourseByPrimaryKey(@Param("id") String id);

    List<Course> selectCouserListByTag(@Param("tag") String tag);

    List<CourseSpinner> selectCourseSpinnerList();

    int checkCourseExist(@Param("id") String id);

    int updateImageIdAfterUpload(@Param("id") String id, @Param("image") String image);

    String findFinalExamByPrimaryKey(@Param("id") String id);

    int updateFinalExamAfterUpload(@Param("id") String id, @Param("finalExam") String finalExam);

    List<Course> selectCourseListByStudent(@Param("student") String student);

    String fingCourseIdByFinalExam(@Param("finalExam")String finalExam);

    String findTeacherIdByPrimaryKey(@Param("id")String id);

    List<CourseSpinner> selectCourseSpinnerListByTeacher(@Param("teacher")String teacher);

    List<CourseSpinner> selectCourseSpinnerListByStudent(@Param("student") String student);
}
