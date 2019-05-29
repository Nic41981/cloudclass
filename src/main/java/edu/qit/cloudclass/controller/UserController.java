package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import edu.qit.cloudclass.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author nic
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    public static final String SESSION_KEY = "correct_user";
    private static final String AUTO_LOGIN_KEY = "auto_login_taken";

    private final UserService userServer;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse register(@RequestBody(required = false) User user) {
        //参数检查
        if (user == null || !Tool.checkParamsNotNull(user.getName(), user.getPassword(), user.getEmail())) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //调用service方法注册信息
        return userServer.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse login(@RequestBody(required = false) Map<String, String> params, HttpServletResponse response, HttpSession session) {
        //接收并检查参数
        if (params == null) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        String name = params.get("name");
        String password = params.get("password");
        boolean autoLogin = Boolean.parseBoolean(params.get("autoLogin"));
        if (!Tool.checkParamsNotNull(name, password)) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //调用Service方法验证登录
        ServerResponse<User> result = userServer.login(name, password, autoLogin);
        if (!result.isSuccess()) {
            return result;
        }
        //记录会话信息
        session.setAttribute(SESSION_KEY, result.getData());
        if (autoLogin) {
            //Cookie中写入自动登录凭证
            String taken = result.getData().getTaken();
            Cookie cookie = new Cookie(AUTO_LOGIN_KEY, taken);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
        }
        return result;
    }

    @RequestMapping(value = "/login/auto", method = RequestMethod.POST)
    public ServerResponse autoLogin(HttpServletRequest request, HttpSession session) {
        //获取Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "未获取Cookie");
        }
        //遍历查找凭证
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTO_LOGIN_KEY)) {
                //调用Service方法自动登录
                ServerResponse<User> autoLoginResult = userServer.autoLogin(cookie.getValue());
                if (autoLoginResult.isSuccess()) {
                    session.setAttribute(SESSION_KEY, autoLoginResult.getData());
                    return ServerResponse.createBySuccess("登陆成功", autoLoginResult.getData());
                }
            }
        }
        return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "未找到凭证");
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ServerResponse logout(HttpSession session,HttpServletResponse response){
        session.removeAttribute(SESSION_KEY);
        Cookie cookie = new Cookie(AUTO_LOGIN_KEY,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ServerResponse.createBySuccessMsg("退出登录成功");
    }

}
