package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.FileMapper;
import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.service.FileService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileServiceImpl implements FileService {

    public static final String FILE_BASE_PATH = "/usr/cloudclass/files";
    private final FileMapper fileMapper;

    @Override
    public ServerResponse<FileInfo> getFileInfo(String fileId) {
        FileInfo fileInfo = fileMapper.findFileByPrimaryKey(fileId);
        if (fileInfo == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "文件不存在");
        }
        return ServerResponse.createBySuccess(fileInfo);
    }

    @Override
    public ServerResponse getFileNames(String fileId) {
        ServerResponse<FileInfo> fileInfoResult = getFileInfo(fileId);
        if (!fileInfoResult.isSuccess()) {
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getData();
        String realName = fileInfo.getRealName() + "." + fileInfo.getSuffix();
        String resourceName = fileInfo.getId() + "." + fileInfo.getSuffix();
        Map<String, String> result = new HashMap<>(2);
        result.put("realName", realName);
        result.put("resourceName", resourceName);
        return ServerResponse.createBySuccess("查询成功", result);
    }

    @Override
    public ServerResponse associateDelete(String fileId) {
        //查找文件记录
        ServerResponse<FileInfo> fileInfoResult = getFileInfo(fileId);
        if (!fileInfoResult.isSuccess()) {
            log.warn("文件记录不存在");
            return ServerResponse.createBySuccess();
        }
        FileInfo fileInfo = fileInfoResult.getData();
        //删除文件实体
        String path = FILE_BASE_PATH + File.separator
                + fileInfo.getType() + File.separator
                + fileInfo.getId() + "." + fileInfo.getSuffix();
        log.info("删除文件:" + path);
        File file = new File(path);
        if (!file.exists()) {
            log.warn("文件不存在");
        } else if (!file.delete()) {
            log.warn("文件无法删除");
        }
        //删除文件记录
        log.info("删除文件记录:" + fileInfo.toString());
        if (fileMapper.delete(fileId) == 0) {
            log.warn("文件记录删除失败");
        }
        return ServerResponse.createBySuccess();
    }
}
