package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.FileMapper;
import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.service.FileService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nic
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;

    @Override
    public ServerResponse<FileInfo> getFileInfo(String fileId) {
        FileInfo fileInfo = fileMapper.findAllByPrimaryKey(fileId);
        if (fileInfo == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"文件不存在");
        }
        return ServerResponse.createBySuccess(fileInfo);
    }

    @Override
    public ServerResponse getFileNames(String fileId) {
        ServerResponse<FileInfo> fileInfoResult = getFileInfo(fileId);
        if (!fileInfoResult.isSuccess()){
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getData();
        String realName = fileInfo.getRealName() + "." + fileInfo.getSuffix();
        String resourceName = fileInfo.getId() + "." + fileInfo.getSuffix();
        Map<String,String> result = new HashMap<>(2);
        result.put("realName",realName);
        result.put("resourceName",resourceName);
        return ServerResponse.createBySuccess("查询成功",result);
    }
}
