package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.tool.ServerResponse;
import org.springframework.web.multipart.MultipartFile;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author nic
 * @version 1.0
 */
public interface UploadService {

    ServerResponse uploadImage(MultipartFile multipartFile,String courseId);

    ServerResponse<FileInfo> parserFileInfo(MultipartFile multipartFile);

    ServerResponse<Image> decodeImage(MultipartFile multipartFile);

    ServerResponse<BufferedImage> securityProcessor(Image image);

    ServerResponse storageImage(BufferedImage buffImg,FileInfo fileInfo,String courseId);
}
