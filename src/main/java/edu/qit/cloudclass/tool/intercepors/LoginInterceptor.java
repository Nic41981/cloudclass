package edu.qit.cloudclass.tool.intercepors;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.qit.cloudclass.controller.UserController;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-19
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            try {
                request.getRequestDispatcher("/error/noLogin").forward(request,response);
            } catch (Exception e) {
                log.error("拦截器转发失败",e);
            }
            return false;
        }
        return true;
    }
}
