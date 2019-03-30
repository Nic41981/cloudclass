package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.service.UploadService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("/course/image/{courseId}")
    public ServerResponse imageUpload(@PathVariable("courseId")String courseId, @RequestParam(value = "image",required = false)MultipartFile image){
        if (image == null || image.isEmpty()){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"未接收到文件");
        }
        //TODO 权限判断
        return uploadService.uploadImage(image,courseId);
    }
}
