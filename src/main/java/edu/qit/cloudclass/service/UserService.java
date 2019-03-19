package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface UserService {
    /**
     * 用户注册
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse<Void> register(String name,String password,String email);

    /**
     * 用户登录
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse<User> login(String name, String password);

    /**
     * 产生并记录用户自动登录凭证
     * @author 董悦
     * 2019-03-19
     */
    String registerAutoLogin(User user);

    /**
     * 用户自动登录
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse<User> autoLogin(String taken);
}
