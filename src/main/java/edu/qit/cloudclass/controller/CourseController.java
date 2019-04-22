package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.CourseService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/course")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {
    private final CourseService courseService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
    public ServerResponse getAllTag(@PathVariable("tag") String tag) {
        //查询课程
        return courseService.tagList(tag);
    }

    @RequestMapping(value = "/chapter/list", method = RequestMethod.GET)
    public ServerResponse getChapterList(@RequestParam(value = "course",required = false) String courseId, HttpSession session) {
        if (!Tool.checkParamsNotNull(courseId)) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        if (!permissionService.isCourseExist(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (!permissionService.isMemberOfCourse(user.getId(),courseId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"未参加学习");
        }
        //查询课程
        return courseService.courseChapterList(courseId);
    }

    @RequestMapping("/notice")
    public ServerResponse getNotices(@RequestParam("course")String courseId){
        if (!Tool.checkParamsNotNull(courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        if (!permissionService.isCourseExist(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        return courseService.getNotices(courseId);
    }
}
