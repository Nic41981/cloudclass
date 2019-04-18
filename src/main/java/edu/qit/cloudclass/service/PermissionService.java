package edu.qit.cloudclass.service;

import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface PermissionService {
    ServerResponse checkCourseOwnerPermission(String userId, String courseId);

    ServerResponse checkChapterOwnerPermission(String userId, String courseId, String chapterId);

    ServerResponse checkCourseMemberPermission(String userId,String courseId);
}
