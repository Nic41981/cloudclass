package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.service.ExaminationService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author nic
 * @version 1.0
 */
@RestController
@RequestMapping("/examination")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExaminationController {

    private final PermissionService permissionService;
    private final ExaminationService examinationService;

    @RequestMapping(value = "/{courseId}",method = RequestMethod.POST)
    public ServerResponse createFinalExam(
            @PathVariable("courseId")String courseId,
            @RequestBody(required = false) ExamComplex exam,
            HttpSession session){
        //参数检查
        if (exam == null || !Tool.checkParamsNotNull(exam.getName()) ){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        if (exam.getStartTime() == null || exam.getStopTime() == null || exam.getDuration() == 0){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        if (exam.getChoiceList() == null || exam.getJudgementList() == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        //权限检查
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(user.getId(),courseId);
        if (!permissionResult.isSuccess()){
            return permissionResult;
        }
        exam.setCourse(courseId);
        //创建期末考试
        return examinationService.createFinalExam(exam,courseId);
    }

    @RequestMapping(value = "/{courseId}/{chapterId}",method = RequestMethod.POST)
    public ServerResponse createChapterExam(
            @PathVariable("courseId")String courseId,
            @PathVariable("chapterId")String chapterId,
            @RequestBody(required = false) ExamComplex exam,
            HttpSession session){
        //参数检查
        if (exam == null || !Tool.checkParamsNotNull(exam.getName()) ){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        if (exam.getStartTime() == null || exam.getStopTime() == null || exam.getDuration() == 0){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        if (exam.getChoiceList() == null || exam.getJudgementList() == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        //权限检查
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse permissionResult = permissionService.checkChapterOwnerPermission(user.getId(),courseId,chapterId);
        if (!permissionResult.isSuccess()){
            return permissionResult;
        }
        exam.setCourse(courseId);
        //创建章节测试
        return examinationService.createChapterExam(exam,chapterId);
    }

    @RequestMapping(value = "/{examId}",method = RequestMethod.GET)
    public ServerResponse getExamPage(@PathVariable("examId")String examId,HttpSession session){
        //权限检查
        User user = (User)session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        ServerResponse permissionResult = permissionService.checkExamReadPermission(user.getId(),examId);
        if (!permissionResult.isSuccess()){
            return permissionResult;
        }
        //查询试卷
        return examinationService.getExamPage(examId);
    }
}
