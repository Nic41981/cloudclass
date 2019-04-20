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
    private final FileMapper fileMapper;

    @Override
    public ServerResponse getFileInfo(String fileId) {
        FileInfo fileInfo = fileMapper.findFileByPrimaryKey(fileId);
        if (fileInfo == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"文件不存在");
        }
        return ServerResponse.createBySuccess("查询成功",fileInfo);
    }

    @Override
    public void associateDelete(String fileId) {
        //查找文件记录
        FileInfo fileInfo = fileMapper.findFileByPrimaryKey(fileId);
        if (fileInfo == null) {
            log.warn("查找文件记录失败");
            return;
        }
        //删除文件实体
        String path = fileInfo.getFilePath() + fileInfo.getId() + "." + fileInfo.getSuffix();
        log.info("删除文件:" + path);
        File file = new File(path);
        if (!file.exists()) {
            log.warn("文件不存在");
        }
        else if (!file.delete()) {
            log.warn("文件无法删除");
        }
        //删除文件记录
        log.info("删除文件记录:" + fileInfo.toString());
        if (fileMapper.delete(fileId) == 0) {
            log.warn("文件记录删除失败");
        }
    }
}
