package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.ChapterExam;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.service.ChapterExamService;
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
 * @date 19-4-17
 */
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChapterExamController {

    private final ChapterExamService chapterExamService;

    @RequestMapping(value = "/chapter", method = RequestMethod.POST)
    public ServerResponse createChapterExam(
            @RequestBody(required = false) ChapterExam exam,
            HttpSession session) {
        //参数检查
        if (exam == null || !exam.isCompleteExamination()) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //创建章节测试
        return chapterExamService.create(exam, user.getId());
    }

    @RequestMapping(value = "/chapter/{examId}", method = RequestMethod.GET)
    public ServerResponse getExamPage(@PathVariable("examId") String examId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "用户未登录");
        }
        //查询试卷
        return chapterExamService.page(examId, user.getId());
    }

    @RequestMapping(value = "/chapter/submit", method = RequestMethod.POST)
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
        return chapterExamService.submit(answer);
    }

    @RequestMapping(value = "/chapter/score/{chapterId}", method = RequestMethod.GET)
    public ServerResponse chapterExamScore(@PathVariable("chapterId") String chapterId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "权限不足");
        }
        return chapterExamService.score(user.getId(), chapterId);
    }
}
