package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.TStatisticsService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Root
 */
@RestController
@RequestMapping("/teacher/statistics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TStatisticsController {

    private final TStatisticsService tStatisticsService;
    private final PermissionService permissionService;

    /**
     * 选课统计
     */
    @RequestMapping(value = "/student",method = RequestMethod.GET)
    public ServerResponse studentAnalysis(@RequestParam(value = "course",required = false) String courseId, HttpSession session) {
        if (!Tool.checkParamsNotNull(courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        if (!permissionService.isCourseExist(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),courseId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        return tStatisticsService.studentStatistics(courseId);
    }

    /**
     * 成绩统计
     */
    @RequestMapping(value = "/score",method = RequestMethod.GET)
    public ServerResponse scoreAnalysis(@RequestParam(value = "exam",required = false)String examId, HttpSession session){
        if (!Tool.checkParamsNotNull(examId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        String courseId;
        if (permissionService.isFinalExamExist(examId)){
            courseId = permissionService.getCourseIdByFinalExam(examId);
            if (courseId == null){
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
            }
        }
        else if (permissionService.isChapterExamExist(examId)){
            courseId = permissionService.getCourseIdByChapterExam(examId);
            if (courseId == null){
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
            }
            if (!permissionService.isCourseExist(courseId)){
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
            }
        }
        else {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"考试不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),courseId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        return tStatisticsService.scoreStatistics(examId);
    }
}
