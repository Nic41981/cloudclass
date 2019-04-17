package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.domain.StudyItem;
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
    List<StudyItem> findStudyByCourseId(@Param("course")String course);
}
