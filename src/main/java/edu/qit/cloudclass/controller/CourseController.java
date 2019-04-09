package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.CourseService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/course")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {
    private final CourseService courseService;

    @RequestMapping(value = "/tag/{tag}",method = RequestMethod.GET)
    public ServerResponse getAllTag(@PathVariable("tag") String tag, @RequestBody(required = false) Course course){
        //查询课程
        return courseService.TagList(tag);
    }


}
