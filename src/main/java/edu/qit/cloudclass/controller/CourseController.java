package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.service.impl.TCourseService;

import com.github.pagehelper.PageHelper;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.service.impl.CourseServiceImpl;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/teacher/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseServiceImpl courseServiceImpl;

    @RequestMapping(value = "findCourseById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Course> findCourseById(String id) {
        return courseServiceImpl.findCourseById(id);
    }

    @RequestMapping(value = "deleteCourseById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteCourseById(String id){
        return courseServiceImpl.deleteCourseById(id);
    }

    @RequestMapping(value = "modify.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modify(
                                 @RequestParam(value = "id") String id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "image", required = false) String image,
                                 @RequestParam(value = "createTime", required = false)Date createTime,
                                 @RequestParam(value = "teacher", required = false) String teacher,
                                 @RequestParam(value = "tag",required = false) String tag) {
        //需要允许另一条件的参数为空。否则没传另一条件的参数会报错 @RequestParam required = false;
        Course course = new Course(id,name,image,createTime,teacher,tag);
//        return tCourseService.modify(course);
        ServerResponse response = courseServiceImpl.modify(course);
        return response;
    }
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Course course){
        return courseServiceImpl.add(course);
    }

    @RequestMapping(value = "getCourses.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCourses(@RequestParam(defaultValue = "1" ) int pageNo, @RequestParam(defaultValue = "5") int pageSize,@Param("teacher")String teacher){
        PageHelper.startPage(pageNo,pageSize);
        return courseServiceImpl.getCourses(pageNo,pageSize,teacher);//这个查询不会分页
    }

}
