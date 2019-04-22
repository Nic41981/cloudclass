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
    /**
     * 李九龙
     */
    int insert(@Param("course") Course course);

    int delete(@Param("id") String id);

    int modify(@Param("course") Course course);

    List<Course> coursesList(@Param("teacher") String teacher);

    Course findCourseByPrimaryKey(@Param("id") String id);

    List<Course> tagList(@Param("tag") String tag);

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
    int updateImageIdAfterUpload(@Param("id") String id, @Param("image") String image);

    /**
     * 董悦
     */
    String findFinalExamByPrimaryKey(@Param("id") String id);

    /**
     * 董悦
     */
    int updateFinalExamAfterUpload(@Param("id") String id, @Param("finalExam") String finalExam);

    /**
     * 李广源
     */
    List<Course> getSCourseList(@Param("student") String student);

    /**
     * 李广源
     */
    int checkStudyByCourseIdAndStudentId(Study study);

    /**
     * 李广源
     */
    int deletScourseByCourseIdAndStudentId(Study study);

    String fingCourseIdByFinalExam(@Param("finalExam")String finalExam);

    String findTeacherIdByPrimaryKey(@Param("id")String id);

    List<CourseSpinner> selectCourseSpinnerListByTeacher(@Param("teacher")String teacher);

    List<CourseSpinner> selectCourseSpinnerListByStudent(@Param("student") String student);
}
