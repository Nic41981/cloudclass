package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface PermissionService {
    ServerResponse checkCourseOwnerPermission(User user, String courseId);
}
