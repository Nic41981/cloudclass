package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Study;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface StudyMapper {
    Study findStudyByCourseAndStudent(@Param("course")String course,@Param("student")String student);
}
