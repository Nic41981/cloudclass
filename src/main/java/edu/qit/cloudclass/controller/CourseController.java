package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.service.CourseService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {
    private final CourseService courseService;

    @RequestMapping(value = "/tag/{tag}",method = RequestMethod.GET)
    public ServerResponse getAllTag(@PathVariable("tag") String tag){
        //查询课程
        return courseService.tagList(tag);
    }

    @RequestMapping(value = "/chapter/list/{course}",method = RequestMethod.GET)
    public ServerResponse getChapterList(@PathVariable("course") String course){
        //查询课程
        return courseService.courseChapterList(course);
    }
}
