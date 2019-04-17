package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.ScoreItem;
import edu.qit.cloudclass.service.SpinnerService;
import edu.qit.cloudclass.service.TStatisticsService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Root
 */
@RestController
@RequestMapping("/teacher/statistics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TStatisticsController {

    private final TStatisticsService tStatisticsService;

    //查询课程列表
    @RequestMapping(value = "/course",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCourseList() {
        return tStatisticsService.getCourseList();
    }

    //查询章节列表
    @RequestMapping(value = "/chapter",method = RequestMethod.GET)
    public ServerResponse getChapterList(@RequestParam(value = "course",required = false) String courseId) {
        if (!Tool.checkParamsNotNull(courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        return tStatisticsService.getChapterList(courseId);
    }

    //查询学生成绩列表
    @RequestMapping(value = "/score",method = RequestMethod.GET)
    public ServerResponse getScoreList(@RequestParam(value = "exam",required = false) String examId) {
        if (!Tool.checkParamsNotNull(examId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        return tStatisticsService.getScoreList(examId);
    }


    //查询学生参加课程表
    @RequestMapping(value = "/study",method = RequestMethod.GET)
    public ServerResponse getStudyList(@RequestParam(value = "course",required = false) String courseId) {
        if (!Tool.checkParamsNotNull(courseId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        return tStatisticsService.getStudyList(courseId);
    }

    //查询学生成绩百分比
    @RequestMapping(value = "/score/analysis",method = RequestMethod.GET)
    public ServerResponse getScorePercent(@RequestParam(value = "exam",required = false) String examId) {
        if (!Tool.checkParamsNotNull(examId)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        return tStatisticsService.getScorePercent(examId);
    }


}
