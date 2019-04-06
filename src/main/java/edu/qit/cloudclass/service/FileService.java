package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface FileService {

    ServerResponse<FileInfo> getFileInfo(String fileId);

    ServerResponse getFileNames(String fileId);

    ServerResponse associateDelete(String fileId);
}
