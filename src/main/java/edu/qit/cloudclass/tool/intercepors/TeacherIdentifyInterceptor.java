package edu.qit.cloudclass.tool.intercepors;

import edu.qit.cloudclass.controller.UserController;
import edu.qit.cloudclass.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-19
 */
@Slf4j
@Component
public class TeacherIdentifyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        if (User.TEACHER_IDENTITY != user.getIdentity()){
            try{
                request.getRequestDispatcher("/error/noPermission").forward(request,response);
            }catch (Exception e){
                log.info("拦截器转发失败",e);
            }
            return false;
        }
        return true;
    }
}
