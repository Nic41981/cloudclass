package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.domain.Course;

import com.github.pagehelper.PageHelper;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.impl.TCourseServiceImpl;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseController {

    private final TCourseServiceImpl tCourseServiceImpl;
    private final CourseMapper courseMapper;
    private final PermissionService permissionService;

    @RequestMapping(value = "/course/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Course> findCourseById(@PathVariable("id") String id) {

        return tCourseServiceImpl.findCourseById(id);
    }

    @RequestMapping(value = "/course/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteCourseById(@PathVariable("id") String id, HttpSession session){
        User user = (User) session.getAttribute(UserController.SESSION_KEY);

        /**
         * 判断 有没有这个课程 返回的json字符 有没有用户登录时情况
         */
        int temp = courseMapper.selectCourseId(id);
        if (temp == 0) {
            return ServerResponse.createByError("没有此课程");
        }
        if (user == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"当前用户没有登录");
        }
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(user.getName(),id);

        if (!permissionResult.isSuccess()){
            return ServerResponse.createByError("权限不足");
        }else{
            log.info("课程删除成功 " );
            return tCourseServiceImpl.deleteCourseById(id);
        }
    }

    @RequestMapping(value = "/course/{CourseId}",method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse modify(@PathVariable("CourseId") String CourseId, @RequestBody(required = false) Map<String,String> params, HttpSession session) {
        //需要允许另一条件的参数为空。否则没传另一条件的参数会报错 @RequestParam required = false;

        User user = (User) session.getAttribute(UserController.SESSION_KEY);

        int temp = courseMapper.selectCourseId(CourseId);
        if (temp == 0) {
            return ServerResponse.createByError("没有此课程");
        }
        if (user == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"当前用户没有登录");
        }
        ServerResponse resultResponse = permissionService.checkCourseOwnerPermission(user.getName(),CourseId);
        if (!resultResponse.isSuccess()){
            log.info("权限不足");
            return resultResponse;
        }
        Course ttCourse = new Course();
        ttCourse.setId(CourseId);
        ttCourse.setName(params.get("name"));
        ttCourse.setImage(params.get("image"));
        ttCourse.setTeacher(params.get("teacher"));
        ttCourse.setTag(params.get("tag"));
        return tCourseServiceImpl.modify(ttCourse);

    }
    @RequestMapping(value = "/course",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Course course){
        return tCourseServiceImpl.add(course);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCourses(@RequestParam(defaultValue = "1" ) int pageNo, @RequestParam(defaultValue = "5") int pageSize,@Param("teacher") String teacher,HttpSession session){
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        PageHelper.startPage(pageNo,pageSize);
        /**
         * 登录进去之后 才能查寻当前老师的所有课程信息
         */
        teacher = user.getName();
        return tCourseServiceImpl.getCourses(pageNo,pageSize,teacher);//这个查询不会分页
    }
    @RequestMapping(value = "/course/list",method = RequestMethod.GET)
    public ServerResponse getCourseList(){

//        if (teacher == null){
//            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
//        }
        return tCourseServiceImpl.getCourseList();
    }
}
