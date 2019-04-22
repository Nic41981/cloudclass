package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.FileMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.FileInfo;
import edu.qit.cloudclass.service.FileService;
import edu.qit.cloudclass.service.UploadService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadServiceImpl implements UploadService {

    private final FileMapper fileMapper;
    private final ChapterMapper chapterMapper;
    private final CourseMapper courseMapper;
    private final FileService fileService;

    private static final String BASE_FILE_PATH = "/usr/cloudclass/files/";
    private static final String BASE_MAPPING_PATH = "/files/";

    @Override
    public ServerResponse uploadCourseImage(MultipartFile multipartFile, String courseId) {
        log.info("==========上传图片开始==========");
        //解析文件名信息
        ServerResponse<FileInfo> fileInfoResult = parserSimpleFileInfo(multipartFile);
        if (!fileInfoResult.isSuccess()) {
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = addCourseImagePath(fileInfoResult.getData(),courseId);
        log.info("上传文件:" + fileInfo.toString());
        //安全检查
        ServerResponse securityCheckResult = courseImageSecurityCheck(multipartFile, fileInfo);
        if (!securityCheckResult.isSuccess()) {
            log.error("==========安全检查失败==========");
            return securityCheckResult;
        }
        //文件存储
        ServerResponse storageResult = storageCourseImage(multipartFile, fileInfo, courseId);
        if (!storageResult.isSuccess()) {
            log.error("==========图片存储出错==========");
            return storageResult;
        }
        log.info("==========图片上传结束==========");
        return storageResult;
    }

    @Override
    public ServerResponse uploadChapterVideo(MultipartFile multipartFile, String chapterId) {
        log.info("==========上传视频开始==========");
        //解析文件名信息
        ServerResponse<FileInfo> fileInfoResult = parserSimpleFileInfo(multipartFile);
        if (!fileInfoResult.isSuccess()) {
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = addChapterVideoPath(fileInfoResult.getData(),chapterId);
        log.info("上传文件:" + fileInfo.toString());
        //安全检查
        ServerResponse securityCheckResult = chapterVideoSecurityCheck(multipartFile, fileInfo);
        if (!securityCheckResult.isSuccess()) {
            log.error("==========安全检查失败==========");
            return securityCheckResult;
        }
        //文件存储
        ServerResponse storageResult = storageChapterVideo(multipartFile, fileInfo, chapterId);
        if (!storageResult.isSuccess()) {
            log.error("==========视频转存失败==========");
            return storageResult;
        }
        log.info("==========上传视频结束==========");
        return storageResult;
    }

    private ServerResponse<FileInfo> parserSimpleFileInfo(MultipartFile multipartFile){
        String filename = multipartFile.getOriginalFilename();
        if (filename == null || filename.length() <= 0) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        int suffixIndex = filename.lastIndexOf('.');
        if (suffixIndex <= 0) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        String realName = filename.substring(0, suffixIndex);
        String suffix = filename.substring(suffixIndex + 1);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(Tool.uuid());
        fileInfo.setRealName(realName);
        fileInfo.setSuffix(suffix);
        return ServerResponse.createBySuccess(fileInfo);
    }

    /**
     * BASE_FILE_PATH = /usr/cloudclass/files/
     * BASE_MAPPING_PATH = /files/
     * relativePath = image/course/课程ID
     */
    private FileInfo addCourseImagePath(FileInfo fileInfo, String courseId){
        String relativePath = "image" + File.separator
                + "course" + File.separator
                + courseId + File.separator;
        String filePath = BASE_FILE_PATH  + relativePath;
        String mappingPath = BASE_MAPPING_PATH + relativePath;
        fileInfo.setFilePath(filePath);
        fileInfo.setMappingPath(mappingPath);
        return fileInfo;
    }

    /**
     * BASE_FILE_PATH = /usr/cloudclass/files/
     * BASE_MAPPING_PATH = /files/
     * relativePath = video/chapter/章节ID
     */
    private FileInfo addChapterVideoPath(FileInfo fileInfo, String chapterId){
        String relativePath = "video" + File.separator
                + "chapter" + File.separator
                + chapterId + File.separator;
        String filePath = BASE_FILE_PATH  + relativePath;
        String mappingPath = BASE_MAPPING_PATH + relativePath;
        fileInfo.setFilePath(filePath);
        fileInfo.setMappingPath(mappingPath);
        return fileInfo;
    }


    private ServerResponse courseImageSecurityCheck(MultipartFile multipartFile,FileInfo fileInfo){
        try {
            if (!Tool.checkSupportImageType(fileInfo.getSuffix())) {
                throw new Exception("后缀名类型不支持");
            }
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            if (image == null || image.getHeight(null) <= 0 || image.getWidth(null) <= 0) {
                throw new Exception("图片尺寸非法");
            }
            log.info("图片尺寸:" + image.getWidth(null) + "x" + image.getHeight(null));
        } catch (Exception e) {
            log.error("安全检查失败", e);
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        return ServerResponse.createBySuccess();
    }

    private ServerResponse chapterVideoSecurityCheck(MultipartFile multipartFile,FileInfo fileInfo){
        try {
            if (!Tool.checkSupportVideoType(fileInfo.getSuffix())) {
                throw new Exception("后缀名类型不支持");
            }
            if (multipartFile.isEmpty()){
                throw new Exception("视频大小非法");
            }
        } catch (Exception e) {
            log.error("安全检查失败", e);
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        return ServerResponse.createBySuccess();
    }

    private ServerResponse storageCourseImage(MultipartFile multipartFile, FileInfo fileInfo, String courseId){
        File file = new File(fileInfo.getFilePath() + fileInfo.getId() + "." + fileInfo.getSuffix());
        File dir = new File(fileInfo.getFilePath());
        try{
            if (!dir.exists() && !dir.mkdirs()){
                throw new Exception("文件目录创建失败");
            }
            Course course = courseMapper.findCourseByPrimaryKey(courseId);
            if (course.getImage() != null) {
                fileService.associateDelete(course.getImage());
            }
            //存储文件
            multipartFile.transferTo(file);
            //更新数据库
            fileMapper.insert(fileInfo);
            courseMapper.updateImageIdAfterUpload(courseId, fileInfo.getId());
        }catch (Exception e){
            log.error("图片存储失败",e);
            return ServerResponse.createByError("上传失败");
        }
        return ServerResponse.createBySuccessMsg("上传成功");
    }

    private ServerResponse storageChapterVideo(MultipartFile multipartFile, FileInfo fileInfo, String chapterId){
        File file = new File(fileInfo.getFilePath() + fileInfo.getId() + "." + fileInfo.getSuffix());
        File dir = new File(fileInfo.getFilePath());
        try{
            if (!dir.exists() && !dir.mkdirs()){
                throw new Exception("文件目录创建失败");
            }
            Chapter chapter = chapterMapper.findChapterByPrimaryKey(chapterId);
            if (chapter.getVideo() != null) {
                fileService.associateDelete(chapter.getVideo());
            }
            //存储文件
            multipartFile.transferTo(file);
            //更新数据库
            fileMapper.insert(fileInfo);
            chapterMapper.updateVideoIdAfterUpload(chapterId, fileInfo.getId());
        }catch (Exception e){
            log.error("视频存储失败",e);
            return ServerResponse.createByError("上传失败");
        }
        return ServerResponse.createBySuccessMsg("上传成功");
    }
}
