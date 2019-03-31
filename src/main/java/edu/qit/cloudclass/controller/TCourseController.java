package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.TCourse;
import edu.qit.cloudclass.service.impl.TCourseService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/tcourse")
@Slf4j
public class TCourseController {
    @Autowired
    private TCourseService tCourseService;

    @RequestMapping(value = "findCourseById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<TCourse> findCourseById(String id) {
        return tCourseService.findCourseById(id);
    }

    @RequestMapping(value = "deleteCourseById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteCourseById(String id){
        return tCourseService.deleteCourseById(id);
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
        TCourse tCourse = new TCourse(id,name,image,createTime,teacher,tag);
//        return tCourseService.modify(tCourse);
        ServerResponse response = tCourseService.modify(tCourse);
        return response;
    }
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(TCourse tCourse){
        return tCourseService.add(tCourse);
    }
}
