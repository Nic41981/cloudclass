package edu.qit.cloudclass.controller;


import edu.qit.cloudclass.service.SpinnerService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Root
 */

@RestController
@RequestMapping("/spinner")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SpinnerController {

    private final SpinnerService spinnerService;

    //查询课程列表
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCourseList() {
        return spinnerService.getCourseList();
    }

    //查询章节列表
    @RequestMapping(value = "/chapter", method = RequestMethod.GET)
    public ServerResponse getChapterList(@RequestParam(value = "course", required = false) String courseId) {
        if (!Tool.checkParamsNotNull(courseId)) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        return spinnerService.getChapterList(courseId);
    }

    //查询考试名称列表
    @RequestMapping(value = "/examination", method = RequestMethod.GET)
    public ServerResponse getExaminationList(@RequestParam(value = "course", required = false) String courseId) {
        if (!Tool.checkParamsNotNull(courseId)) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        return spinnerService.getExaminationList(courseId);
    }
}
