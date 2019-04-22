package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.service.RotationPictureService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-22
 */
@RestController
@RequestMapping("/rotationPicture")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RotationPictureController {

    private final RotationPictureService rotationPictureService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ServerResponse getList(@RequestParam("tag")String tag){
        if (!Tool.checkParamsNotNull(tag)){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        return rotationPictureService.getList(tag);
    }
}
