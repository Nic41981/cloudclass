package edu.qit.cloudclass.tool.intercepors;

import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-22
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpinnerInterceptor implements HandlerInterceptor {

    private final PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String courseId = request.getParameter("course");
        if (!Tool.checkParamsNotNull(courseId)){
            try {
                request.getRequestDispatcher("/error/missingParam").forward(request,response);
            } catch (Exception e) {
                log.error("拦截器转发失败",e);
            }
            return false;
        }
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
