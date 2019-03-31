package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import edu.qit.cloudclass.dao.UserMapper;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nic
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public ServerResponse register(User user) {
        //唯一性检查
        ServerResponse response = checkValid(user.getName(),User.NAME);
        if (!response.isSuccess()){
            return response;
        }
        response = checkValid(user.getEmail(),User.EMAIL);
        if (!response.isSuccess()){
            return response;
        }
        //创建用户对象
        user.setId(Tool.uuid());
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
        user.setIdentity(User.STUDENT_IDENTITY);
        //写入数据库
        userMapper.register(user);
        log.info("用户" + user.getId() + "注册成功!");
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    @Override
    public ServerResponse<User> login(String name, String password,boolean autoLogin) {
        //查询数据库并判断登录
        User user = userMapper.login(name);
        if (user != null && BCrypt.checkpw(password,user.getPassword())){
            log.info("用户" + user.getId() + "登录成功!");
            if (autoLogin){
                //将自动登录凭证写入数据库和用户信息
                user.setTaken(registerAutoLogin(user).getData());
            }
            return ServerResponse.createBySuccess("登录成功",user);
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"用户名或密码错误");
    }

    @Override
    public ServerResponse<User> autoLogin(String taken) {
        //查询数据库并判断登录
        User user = userMapper.autoLogin(taken);
        if (user != null){
            log.info("用户" + user.getId() + "自动登录成功!");
            return ServerResponse.createBySuccess("登录成功",user);
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"登录失败");
    }

    @Override
    public ServerResponse<String> registerAutoLogin(User user) {
        //构建凭证元数据
        String source = user.getId() + user.hashCode() + System.currentTimeMillis();
        //编译自动登录凭证
        String taken = BCrypt.hashpw(source,BCrypt.gensalt());
        //将自动登录凭证写入数据库
        userMapper.registerAutoLogin(user.getId(), taken);
        return ServerResponse.createBySuccess(taken);
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        switch (type) {
            case User.NAME: {
                //用户名唯一性检查
                int count = userMapper.checkName(str);
                if (count > 0) {
                    return ServerResponse.createByError("用户名已存在");
                }
                break;
            }
            case User.EMAIL: {
                //邮箱唯一性检查
                int count = userMapper.checkEmail(str);
                if (count > 0) {
                    return ServerResponse.createByError("该邮箱已注册");
                }
                break;
            }
            default:
                return ServerResponse.createByError("唯一性检查失败");
        }
        return ServerResponse.createBySuccess();
    }
}
