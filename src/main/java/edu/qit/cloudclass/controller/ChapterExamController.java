package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.ChapterExam;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.service.ChapterExamService;
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
 * @date 19-4-17
 */
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChapterExamController {

    private final PermissionService permissionService;
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
        String courseId = permissionService.getCourseIdByChapterId(exam.getChapter());
        if (courseId == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        if(!permissionService.isCourseExist(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),courseId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        //创建章节测试
        return chapterExamService.create(exam, user.getId());
    }

    @RequestMapping(value = "/chapter/{examId}", method = RequestMethod.GET)
    public ServerResponse getExamPage(@PathVariable("examId") String examId) {
        //查询试卷
        return chapterExamService.page(examId);
    }

    @RequestMapping(value = "/chapter/{examId}", method = RequestMethod.POST)
    public ServerResponse submitExam(@PathVariable("examId") String examId, @RequestBody(required = false) AnswerComplex answer, HttpSession session) {
        //参数检查
        if (answer == null || answer.getChoiceList() == null || answer.getJudgementList() == null) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        //提交答案
        return chapterExamService.submit(user.getId(),examId,answer);
    }

    @RequestMapping(value = "/chapter/score/{examId}", method = RequestMethod.GET)
    public ServerResponse chapterExamScore(@PathVariable("examId") String examId, HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        return chapterExamService.score(user.getId(), examId);
    }
}
