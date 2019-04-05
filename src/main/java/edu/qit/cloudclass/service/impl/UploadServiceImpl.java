package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.FileMapper;
import edu.qit.cloudclass.domain.FileInfo;
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
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadServiceImpl implements UploadService {

    private static final String FILE_BASE_PATH = "/usr/cloudclass/files";
    private final FileMapper fileMapper;
    private final ChapterMapper chapterMapper;

    @Override
    public ServerResponse uploadImage(MultipartFile multipartFile,String courseId) {
        log.info("==========上传图片开始==========");
        //解析文件名信息
        ServerResponse<FileInfo> fileInfoResult = parserFileInfo(multipartFile,FileInfo.IMAGE_FILE_TYPE);
        if (!fileInfoResult.isSuccess()){
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getData();
        //安全检查
        ServerResponse securityCheckResult = securityCheck(multipartFile,fileInfo);
        if (!securityCheckResult.isSuccess()){
            log.error("==========安全检查失败==========");
            return securityCheckResult;
        }
        log.info("安全检查:PASS");
        //文件存储
        ServerResponse storageResult = storageImage(multipartFile,fileInfo,courseId);
        if (!storageResult.isSuccess()){
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
        ServerResponse<FileInfo> fileInfoResult = parserFileInfo(multipartFile,FileInfo.VIDEO_FILE_TYPE);
        if (!fileInfoResult.isSuccess()){
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getData();
        //安全检查
        ServerResponse securityCheckResult = securityCheck(multipartFile,fileInfo);
        if (!securityCheckResult.isSuccess()){
            log.error("==========安全检查失败==========");
            return securityCheckResult;
        }
        log.info("安全检查:PASS");
        //文件存储
        ServerResponse storageResult = storageVideo(multipartFile,fileInfo,courseId,chapterId);
        if (!storageResult.isSuccess()){
            log.error("==========视频转存失败==========");
            return storageResult;
        }
        log.info("==========上传视频结束==========");
        return storageResult;
    }

    @Override
    public ServerResponse<FileInfo> parserFileInfo(MultipartFile multipartFile,String fileType) {
        String filename = multipartFile.getOriginalFilename();
        log.info("文件名:" + filename);
        if (filename == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
        }
        int suffixIndex = filename.lastIndexOf('.');
        if (suffixIndex <= 0){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
        }
        String suffix = filename.substring(suffixIndex + 1);
        String realName = filename.substring(0,suffixIndex);
        log.info("文件类型:" + suffix.toUpperCase() + "文件");
        switch (fileType) {
            case FileInfo.IMAGE_FILE_TYPE: {
                if (!Tool.checkSupportImageType(suffix)) {
                    return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
                }
                break;
            }
            case FileInfo.VIDEO_FILE_TYPE:{
                if (!Tool.checkSupportVideoType(suffix)) {
                    return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
                }
                break;
            }
            default:{
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "不支持的文件类型");
            }
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setRealName(realName);
        fileInfo.setSuffix(suffix);
        return ServerResponse.createBySuccess(fileInfo);
    }

    @Override
    public ServerResponse securityCheck(MultipartFile multipartFile, FileInfo fileInfo) {
        //TODO 安全检查待完善
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse storageImage(MultipartFile multipartFile,FileInfo fileInfo,String courseId) {
        fileInfo.setId(Tool.uuid());
        String path =
                FILE_BASE_PATH + File.separator
                + courseId + File.separator
                + "image" + File.separator
                + fileInfo.getId() + "." + fileInfo.getSuffix();
        File file = new File(path);
        File dir = file.getParentFile();
        try {
            //检查父文件夹
            if (!dir.exists() && !dir.mkdirs()) {
                return ServerResponse.createByError("上传失败");
            }
            //检查文件冲突
            if (file.exists()) {
                return ServerResponse.createByError("上传失败");
            }
            //读取文件流
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            if (image == null || image.getHeight(null) <= 0 || image.getWidth(null) <= 0){
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
            }
            log.info("图片尺寸:" + image.getWidth(null) + "x" + image.getHeight(null));
            //存储文件
            if (!file.createNewFile()){
                return ServerResponse.createByError("上传失败");
            }
            OutputStream os = new FileOutputStream(file);
            ImageIO.write(image, fileInfo.getSuffix(), os);
            fileMapper.insert(fileInfo);
            return ServerResponse.createBySuccess("上传成功", fileInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ServerResponse.createByError("上传失败");
    }

    @Override
    public ServerResponse storageVideo(MultipartFile multipartFile,FileInfo fileInfo,String courseId,String chapterId) {
        fileInfo.setId(Tool.uuid());
        String path =
                FILE_BASE_PATH + File.separator
                + chapterId + File.separator
                + "video" + File.separator
                + chapterId + File.separator
                + fileInfo.getId() + "." + fileInfo.getSuffix();
        File video = new File(path);
        File dir = video.getParentFile();
        try{
            //检查父文件夹
            if (!dir.exists() && !dir.mkdirs()) {
                return ServerResponse.createByError("上传失败");
            }
            //检查文件冲突
            if (video.exists()) {
                return ServerResponse.createByError("上传失败");
            }
            //存储文件
            multipartFile.transferTo(video);
            //更新数据库
            fileMapper.insert(fileInfo);
            chapterMapper.updateVideoIdAfterUpload(chapterId,fileInfo.getId());
            return ServerResponse.createBySuccess("上传成功",fileInfo);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return ServerResponse.createByError("上传失败");
    }
}
