package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.TChapterService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/teacher")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TChapterController {

    private final TChapterService TChapterService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/chapter", method = RequestMethod.POST)
    public ServerResponse chapter(Chapter chapter, @RequestParam(required = false)MultipartFile videoFile, HttpSession session) {
        //参数检查
        if (chapter == null || !Tool.checkParamsNotNull(chapter.getName(), chapter.getInfo(), chapter.getCourse())) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //权限判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (!permissionService.isCourseExist(chapter.getCourse())){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),chapter.getCourse())){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        //创建章节
        return TChapterService.chapter(chapter,videoFile);
    }

    @RequestMapping(value = "/chapter/{chapterId}", method = RequestMethod.PUT)
    public ServerResponse chapterModify(@PathVariable("chapterId") String chapterId, @RequestBody(required = false) Chapter chapter) {
        //参数检查
        if (chapter == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        chapter.setId(chapterId);
        //修改章节
        return TChapterService.chapterModify(chapter);
    }


    @RequestMapping(value = "/chapter/{chapterId}", method = RequestMethod.DELETE)
    public ServerResponse chapterDelete(@PathVariable("chapterId") String chapterId) {
        //删除章节
        return TChapterService.chapterDelete(chapterId);
    }
}
