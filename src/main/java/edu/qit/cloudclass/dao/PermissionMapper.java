package edu.qit.cloudclass.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PermissionMapper {
    boolean checkCourseOwnerPermission(@Param("userId")String userId, @Param("courseId")String courseId);
}
