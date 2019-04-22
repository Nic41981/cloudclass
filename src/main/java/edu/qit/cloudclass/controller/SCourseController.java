package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.SCourseService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SCourseController {
    private final SCourseService sCourseService;

    @RequestMapping(value = "/course/list", method = RequestMethod.GET)
    public ServerResponse courseList(HttpSession session) {
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return sCourseService.courseList(user.getId());
    }

    @RequestMapping(value = "/course/spinner",method = RequestMethod.GET)
    public ServerResponse courseSpinner(HttpSession session){
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return sCourseService.courseSpinnerList(user.getId());
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.POST)
    public ServerResponse insertSCourse(@PathVariable("courseId") String courseId, HttpSession session) {
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return sCourseService.joinCourse(courseId, user.getId());
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.DELETE)
    public ServerResponse deleteSCourse(@PathVariable("courseId") String courseId, HttpSession session) {
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return sCourseService.exitCourse(courseId, user.getId());
    }
}
