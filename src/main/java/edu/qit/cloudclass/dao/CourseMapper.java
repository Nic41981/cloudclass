package edu.qit.cloudclass.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface CourseMapper {
    /**
     * 董悦
     */
    String findTeacherIdByPrimaryKey(@Param("id") String id);
}
