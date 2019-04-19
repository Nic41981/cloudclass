package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.CourseService;
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

    @RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
    public ServerResponse getAllTag(@PathVariable("tag") String tag) {
        //查询课程
        return courseService.tagList(tag);
    }

    @RequestMapping(value = "/chapter/list", method = RequestMethod.GET)
    public ServerResponse getChapterList(@RequestParam("course") String courseId, HttpSession session) {
        if (!Tool.checkParamsNotNull(courseId)) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //查询课程
        return courseService.courseChapterList(courseId, user.getId());
    }
}
