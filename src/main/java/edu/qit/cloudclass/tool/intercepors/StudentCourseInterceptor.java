package edu.qit.cloudclass.tool.intercepors;

import edu.qit.cloudclass.controller.UserController;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Permission;
import java.util.Map;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-21
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentCourseInterceptor implements HandlerInterceptor {

    private final PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String courseId = (String)pathVariables.get("courseId");
        if (!permissionService.isCourseExist(courseId)){
            try {
                request.getRequestDispatcher("/error/noCourse").forward(request,response);
            } catch (Exception e) {
                log.error("拦截器转发失败",e);
            }
            return false;
        }
        return true;
    }
}
