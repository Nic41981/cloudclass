package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Notice;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.SCourseService;
import edu.qit.cloudclass.service.TCourseService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseController {

    private final TCourseService tCourseService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/course/list", method = RequestMethod.GET)
    public ServerResponse getCourses(HttpSession session) {
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return tCourseService.getCourses(user.getId());
    }

    @RequestMapping(value = "/course/spinner",method = RequestMethod.GET)
    public ServerResponse courseSpinnerList(HttpSession session){
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return tCourseService.courseSpinner(user.getId());
    }

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public ServerResponse add(@RequestBody(required = false) Course course, HttpSession session) {
        //参数检查
        if (course == null || !Tool.checkParamsNotNull(course.getName())) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        course.setTeacher(user.getId());
        //创建课程
        return tCourseService.add(course);
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.PUT)
    public ServerResponse modify(@PathVariable("courseId") String courseId, @RequestBody(required = false) Course course) {
        //参数检查
        if (course == null) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        course.setId(courseId);
        //修改课程
        return tCourseService.modify(course);

    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.DELETE)
    public ServerResponse deleteCourseById(@PathVariable("courseId") String courseId) {
        //删除课程
        return tCourseService.deleteCourseById(courseId);
    }

    @RequestMapping(value = "/course/notice",method = RequestMethod.POST)
    public ServerResponse publishNotice(@RequestBody(required = false)Notice notice,HttpSession session){
        if (!Tool.checkParamsNotNull(notice.getCourse(),notice.getContent())){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        if (!permissionService.isCourseExist(notice.getCourse())){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),notice.getCourse())){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        return tCourseService.publishNotice(notice);
    }
}
