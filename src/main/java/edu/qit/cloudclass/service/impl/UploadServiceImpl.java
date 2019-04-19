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

import static edu.qit.cloudclass.service.impl.FileServiceImpl.FILE_BASE_PATH;

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

    @Override
    public ServerResponse uploadImage(MultipartFile multipartFile, String courseId) {
        log.info("==========上传图片开始==========");
        //解析文件名信息
        ServerResponse<FileInfo> fileInfoResult = parserFileInfo(multipartFile, FileInfo.IMAGE_FILE_TYPE);
        if (!fileInfoResult.isSuccess()) {
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getData();
        //安全检查
        ServerResponse securityCheckResult = securityCheck(multipartFile, fileInfo);
        if (!securityCheckResult.isSuccess()) {
            log.error("==========安全检查失败==========");
            return securityCheckResult;
        }
        log.info("安全检查:PASS");
        //读取图片信息
        ServerResponse imageInfoResult = parserImageInfo(multipartFile);
        if (!imageInfoResult.isSuccess()) {
            log.error("==========图片信息读取失败==========");
            return imageInfoResult;
        }
        //文件存储
        ServerResponse storageResult = storageFile(multipartFile, fileInfo, courseId);
        if (!storageResult.isSuccess()) {
            log.error("==========图片存储出错==========");
            return storageResult;
        }
        log.info("==========图片上传结束==========");
        return storageResult;
    }

    @Override
    public ServerResponse uploadVideo(MultipartFile multipartFile, String courseId, String chapterId) {
        log.info("==========上传视频开始==========");
        //解析文件名信息
        ServerResponse<FileInfo> fileInfoResult = parserFileInfo(multipartFile, FileInfo.VIDEO_FILE_TYPE);
        if (!fileInfoResult.isSuccess()) {
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getData();
        //安全检查
        ServerResponse securityCheckResult = securityCheck(multipartFile, fileInfo);
        if (!securityCheckResult.isSuccess()) {
            log.error("==========安全检查失败==========");
            return securityCheckResult;
        }
        log.info("安全检查:PASS");
        //读取视频信息
        ServerResponse videoInfoResult = parserVideoInfo(multipartFile);
        if (!videoInfoResult.isSuccess()) {
            log.error("==========图片信息读取失败==========");
            return videoInfoResult;
        }
        //文件存储
        ServerResponse storageResult = storageFile(multipartFile, fileInfo, chapterId);
        if (!storageResult.isSuccess()) {
            log.error("==========视频转存失败==========");
            return storageResult;
        }
        log.info("==========上传视频结束==========");
        return storageResult;
    }

    @Override
    public ServerResponse<FileInfo> parserFileInfo(MultipartFile multipartFile, String fileType) {
        String filename = multipartFile.getOriginalFilename();
        if (filename == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        log.info("文件名:" + filename);
        int suffixIndex = filename.lastIndexOf('.');
        if (suffixIndex <= 0) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        String realName = filename.substring(0, suffixIndex);
        String suffix = filename.substring(suffixIndex + 1);
        log.info("文件类型:" + suffix.toUpperCase() + "文件");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(Tool.uuid());
        fileInfo.setRealName(realName);
        fileInfo.setSuffix(suffix);
        fileInfo.setType(fileType);
        return ServerResponse.createBySuccess(fileInfo);
    }

    @Override
    public ServerResponse securityCheck(MultipartFile multipartFile, FileInfo fileInfo) {
        //TODO 安全检查待完善
        switch (fileInfo.getType()) {
            case FileInfo.IMAGE_FILE_TYPE:
                if (!Tool.checkSupportImageType(fileInfo.getSuffix())) {
                    return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
                }
                break;
            case FileInfo.VIDEO_FILE_TYPE:
                if (!Tool.checkSupportVideoType(fileInfo.getSuffix())) {
                    return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
                }
                break;
            default:
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse parserImageInfo(MultipartFile multipartFile) {
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            if (image == null || image.getHeight(null) <= 0 || image.getWidth(null) <= 0) {
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
            }
            log.info("图片尺寸:" + image.getWidth(null) + "x" + image.getHeight(null));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse parserVideoInfo(MultipartFile multipartFile) {
        //TODO 视频信息读取待完善
        return ServerResponse.createBySuccess();
    }

    public ServerResponse storageFile(MultipartFile multipartFile, FileInfo fileInfo, String target) {
        String path = FILE_BASE_PATH + File.separator
                + fileInfo.getType() + File.separator
                + fileInfo.getId() + "." + fileInfo.getSuffix();
        File file = new File(path);
        File dir = file.getParentFile();
        try {
            //检查父文件夹
            if (!dir.exists() && !dir.mkdirs()) {
                return ServerResponse.createByError("上传失败");
            }
            switch (fileInfo.getType()) {
                case FileInfo.IMAGE_FILE_TYPE: {
                    //冲突处理
                    Course course = courseMapper.findCourseByPrimaryKey(target);
                    if (course.getImage() != null) {
                        fileService.associateDelete(course.getImage());
                    }
                    //存储文件
                    multipartFile.transferTo(file);
                    //更新数据库
                    fileMapper.insert(fileInfo);
                    courseMapper.updateImageIdAfterUpload(target, fileInfo.getId());
                    break;
                }
                case FileInfo.VIDEO_FILE_TYPE: {
                    //冲突处理
                    Chapter chapter = chapterMapper.findChapterByPrimaryKey(target);
                    if (chapter.getVideo() != null) {
                        fileService.associateDelete(chapter.getVideo());
                    }
                    //存储文件
                    multipartFile.transferTo(file);
                    //更新数据库
                    fileMapper.insert(fileInfo);
                    chapterMapper.updateVideoIdAfterUpload(target, fileInfo.getId());
                    break;
                }
                default: {
                    return ServerResponse.createByError("上传失败");
                }
            }
            return ServerResponse.createBySuccessMsg("上传成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ServerResponse.createByError("上传失败");
    }
}
