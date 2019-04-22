package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Study;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface StudyMapper {
    Study findStudyByCourseAndStudent(@Param("course")String course,@Param("student")String student);

    List<String > selectUserNameListByCourse(@Param("course")String course);

    List<Study> findStudyListByCourse(@Param("course") String course);

    int delete(@Param("id") int id);

    int checkStudyExistByCourseAndStudent(@Param("course")String course,@Param("student")String student);

    int insert(@Param("study")Study study);
}
