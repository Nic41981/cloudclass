package edu.qit.cloudclass.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface CourseMapper {

    String selectTeacherId(String courseId);
}
