package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.service.FileService;
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
 */
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {
    private final FileService fileService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ServerResponse fileNames(@RequestParam(value = "file", required = false) String fileId) {
        if (!Tool.checkParamsNotNull(fileId)) {
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(), "缺少参数");
        }
        return fileService.getFileInfo(fileId);
    }
}
