package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.UploadService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;

/**
 * @author nic
 * @version 1.0
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadController {
    private final UploadService uploadService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/course/image", method = RequestMethod.POST)
    public ServerResponse imageUpload(
            @RequestParam(value = "course", required = false) String courseId,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpSession session) {
        //参数检查
        if (!Tool.checkParamsNotNull(courseId) || image == null || image.isEmpty()) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //权限判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (!permissionService.isCourseExist(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),courseId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        return uploadService.uploadCourseImage(image, courseId);
    }

    @RequestMapping(value = "/chapter/video")
    public ServerResponse videoUpload(
            @RequestParam(value = "chapter", required = false) String chapterId,
            @RequestParam(value = "video", required = false) MultipartFile video,
            HttpSession session) {
        //参数检查
        if (!Tool.checkParamsNotNull(chapterId) || video == null || video.isEmpty()) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        //登录判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        //用户权限判断
        String courseId = permissionService.getCourseIdByChapterId(chapterId);
        if (chapterId == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        if (!permissionService.isCourseExist(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        if (!permissionService.isTeacherOfCourse(user.getId(),courseId)){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
        }
        return uploadService.uploadChapterVideo(video, chapterId);
    }
}
