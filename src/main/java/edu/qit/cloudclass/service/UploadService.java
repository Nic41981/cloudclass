package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.tool.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author nic
 * @version 1.0
 */
public interface UploadService {

    ServerResponse uploadImage(MultipartFile multipartFile,String courseId);

    ServerResponse uploadVideo(MultipartFile multipartFile,String courseId,String chapterId);

    ServerResponse<FileInfo> parserFileInfo(MultipartFile multipartFile,String fileType);

    ServerResponse securityCheck(MultipartFile multipartFile, FileInfo fileInfo);

    ServerResponse storageImage(MultipartFile multipartFile,FileInfo fileInfo,String courseId);

    ServerResponse storageVideo(MultipartFile multipartFile,FileInfo fileInfo,String courseId,String chapterId);

}
