package edu.qit.cloudclass.service;

import edu.qit.cloudclass.tool.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author nic
 * @version 1.0
 */
public interface UploadService {

    ServerResponse uploadCourseImage(MultipartFile multipartFile, String courseId);

    ServerResponse uploadChapterVideo(MultipartFile multipartFile, String chapterId);

}
