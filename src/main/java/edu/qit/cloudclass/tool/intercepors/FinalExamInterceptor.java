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
import java.util.Map;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-19
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinalExamInterceptor implements HandlerInterceptor {

    private final PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String examId = (String)pathVariables.get("examId");
        if (!permissionService.isFinalExamExist(examId)){
            try {
                request.getRequestDispatcher("/error/noExam").forward(request,response);
            } catch (Exception e) {
                log.error("拦截器转发失败",e);
            }
            return false;
        }
        String courseId = permissionService.getCourseIdByFinalExam(examId);
        if (courseId == null){
            try {
                request.getRequestDispatcher("/error/noCourse").forward(request,response);
            } catch (Exception e) {
                log.error("拦截器转发失败",e);
            }
            return false;
        }
        if (!permissionService.isMemberOfCourse(user.getId(),courseId)){
            try {
                request.getRequestDispatcher("/error/noStudy").forward(request,response);
            } catch (Exception e) {
                log.error("拦截器转发失败",e);
            }
            return false;
        }
        return true;
    }
}
