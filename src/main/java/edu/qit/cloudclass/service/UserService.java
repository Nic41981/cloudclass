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
     *
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse register(User user);

    /**
     * 用户登录
     *
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse<User> login(String name, String password, boolean autoLogin);

    /**
     * 用户自动登录
     *
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse<User> autoLogin(String taken);

    /**
     * 产生并记录用户自动登录凭证
     *
     * @author 董悦
     * 2019-03-19
     */
    ServerResponse<String> registerAutoLogin(User user);

    /**
     * 检查用户名或电子邮件的唯一性
     *
     * @author 董悦
     * 2019-03-23
     */
    ServerResponse checkValid(String str, String type);
}
