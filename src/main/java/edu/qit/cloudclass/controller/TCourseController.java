package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Course;

import com.github.pagehelper.PageHelper;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.impl.TCourseServiceImpl;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/teacher/course")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseController {

    private final TCourseServiceImpl tCourseServiceImpl;
    private final PermissionService permissionService;

    @RequestMapping(value = "findCourseById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Course> findCourseById(String id) {
        return tCourseServiceImpl.findCourseById(id);
    }

    @RequestMapping(value = "deleteCourseById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteCourseById(String id, HttpSession session){
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        System.out.println(user.getName());
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(user.getName(),id);
        if (!permissionResult.isSuccess()){
            return ServerResponse.createByError("权限不足");
        }else{
            log.info("课程删除成功 " );
            return tCourseServiceImpl.deleteCourseById(id);
        }
//        return tCourseServiceImpl.deleteCourseById(id);
    }

    @RequestMapping(value = "modify.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modify(@RequestBody(required = false) Course course, HttpSession session) {
        //需要允许另一条件的参数为空。否则没传另一条件的参数会报错 @RequestParam required = false;
//        Course course = new Course(id,name,image,createTime,teacher,tag);
////        return tCourseService.modify(course);
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        System.out.println(user.getName());
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(user.getName(),course.getId());
        if (!permissionResult.isSuccess()){
            return ServerResponse.createByError("权限不足");
        }else{
            log.info("课程修改成功 " );
            ServerResponse response = tCourseServiceImpl.modify(course);
            return response;
        }
    }
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Course course){
        return tCourseServiceImpl.add(course);
    }

    @RequestMapping(value = "getCourses.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCourses(@RequestParam(defaultValue = "1" ) int pageNo, @RequestParam(defaultValue = "5") int pageSize,@Param("teacher")String teacher){
        PageHelper.startPage(pageNo,pageSize);
        return tCourseServiceImpl.getCourses(pageNo,pageSize,teacher);//这个查询不会分页
    }

}
