package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.service.ExaminationService;
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

    private final ExaminationService examinationService;

    @RequestMapping(value = "/final", method = RequestMethod.POST)
    public ServerResponse createFinalExam(
            @RequestBody(required = false) ExamComplex exam,
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
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //创建期末考试
        return examinationService.createFinalExam(exam, user.getId());
    }

    @RequestMapping(value = "/chapter/{chapterId}", method = RequestMethod.POST)
    public ServerResponse createChapterExam(
            @PathVariable("chapterId") String chapterId,
            @RequestBody(required = false) ExamComplex exam,
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
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //创建章节测试
        return examinationService.createChapterExam(exam, chapterId, user.getId());
    }

    @RequestMapping(value = "/{examId}", method = RequestMethod.GET)
    public ServerResponse getExamPage(@PathVariable("examId") String examId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //查询试卷
        return examinationService.getExamPage(examId, user.getId());
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ServerResponse submitExam(@RequestBody(required = false) AnswerComplex answer, HttpSession session) {
        //参数检查
        if (answer == null || !Tool.checkParamsNotNull(answer.getExam())) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //提交答案
        answer.setUser(user.getId());
        return examinationService.submitExam(answer);
    }

    @RequestMapping(value = "/score/course/{courseId}", method = RequestMethod.GET)
    public ServerResponse finalExamScore(@PathVariable("courseId") String courseId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "权限不足");
        }
        return examinationService.finalExamScore(user.getId(), courseId);
    }

    @RequestMapping(value = "/score/chapter/{chapterId}", method = RequestMethod.GET)
    public ServerResponse chapterExamScore(@PathVariable("chapterId") String chapterId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "权限不足");
        }
        return examinationService.chapterExamScore(user.getId(), chapterId);
    }
}
