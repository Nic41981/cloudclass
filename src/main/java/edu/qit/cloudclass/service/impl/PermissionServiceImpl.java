package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public class PermissionServiceImpl implements PermissionService {


    @Override
    public ServerResponse checkCourseOwnerPermission(User user, String courseId) {
        return null;
    }
}
