package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.FinalExam;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.service.FinalExamService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinalExamController {

    private final FinalExamService finalExamService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/final", method = RequestMethod.POST)
    public ServerResponse createFinalExam(
            @RequestBody(required = false) FinalExam exam,
            HttpSession session) {
        //参数检查
        if (exam == null || !exam.isCompleteExamination()) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        if (!exam.isExamTimeCorrect()) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "考试时间错误");
        }
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (!permissionService.isCourseExist(exam.getCourse())){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),exam.getCourse())){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        //创建期末考试
        return finalExamService.create(exam);
    }

    @RequestMapping(value = "/final/{examId}", method = RequestMethod.GET)
    public ServerResponse getExamPage(@PathVariable("examId") String examId) {
        //查询试卷
        return finalExamService.page(examId);
    }

    @RequestMapping(value = "/final/{examId}", method = RequestMethod.POST)
    public ServerResponse submitExam(@PathVariable("examId")String examId,@RequestBody(required = false) AnswerComplex answer, HttpSession session) {
        //参数检查
        if (answer == null || answer.getChoiceList() == null || answer.getJudgementList() == null) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        //提交答案
        return finalExamService.submit(user.getId(),examId,answer);
    }

    @RequestMapping(value = "/final/score/{examId}", method = RequestMethod.GET)
    public ServerResponse finalExamScore(@PathVariable("examId") String examId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return finalExamService.score(user.getId(), examId);
    }
}
