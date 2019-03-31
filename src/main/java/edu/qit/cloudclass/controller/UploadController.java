package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.service.UploadService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
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

    @RequestMapping(value = "/course/image/{courseId}",method = RequestMethod.POST)
    public ServerResponse imageUpload(@PathVariable("courseId")String courseId, @RequestParam(value = "image",required = false)MultipartFile image, HttpSession session){
        //登录判断
        User user = (User) session.getAttribute(UserController.SESSION_KEY);
        if (user == null){
            return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"用户未登录");
        }
        //用户权限判断
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(user.getId(),courseId);
        if (!permissionResult.isSuccess()){
            return permissionResult;
        }
        //图片存在判断
        if (image == null || image.isEmpty()){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"未接收到文件");
        }
        return uploadService.uploadImage(image,courseId);
    }
}
