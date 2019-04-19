package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-19
 */
@Slf4j
@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/noLogin")
    public ServerResponse noLogin(){
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"用户未登录");
    }

    @RequestMapping("/noPermission")
    public ServerResponse noPermission(){
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
    }

    @RequestMapping("/missingParam")
    public ServerResponse missingParam(){
        return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
    }

    @RequestMapping("/noCourse")
    public ServerResponse noCourse(){
        return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
    }

    @RequestMapping("/noChapter")
    public ServerResponse noChapter(){
        return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
    }

    @RequestMapping("/noExam")
    public ServerResponse noExam(){
        return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"考试不存在");
    }

    @RequestMapping("/noStudy")
    public ServerResponse noStudy(){
        return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"未参加学习");
    }
}
