package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.FileMapper;
import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.service.RotationPictureService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-22
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RotationPictureServiceImpl implements RotationPictureService {

    private final FileMapper fileMapper;

    @Override
    public ServerResponse getList(String tag) {
        List<FileInfo> rotationPictureList = fileMapper.selectRotationPictureFileListByType(tag);
        return ServerResponse.createBySuccess("查询成功",rotationPictureList);
    }
}
