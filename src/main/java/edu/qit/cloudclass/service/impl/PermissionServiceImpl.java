package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nic
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionServiceImpl implements PermissionService {

    private final CourseMapper courseMapper;

    @Override
    public ServerResponse checkCourseOwnerPermission(String userId, String courseId) {
        String teacher = courseMapper.selectTeacherId(courseId);
        if (teacher == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (teacher.equals(userId)){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
    }
}
