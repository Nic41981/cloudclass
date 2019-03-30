package edu.qit.cloudclass.service.impl;

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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author nic
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadServiceImpl implements UploadService {

    private final FileMapper fileMapper;

    @Override
    public ServerResponse uploadImage(MultipartFile multipartFile,String courseId) {
        log.info("==========上传图片开始==========");
        //解析文件名信息
        ServerResponse<FileInfo> fileInfoResult = parserFileInfo(multipartFile);
        if (!fileInfoResult.isSuccess()){
            log.error("==========文件名解析失败==========");
            return fileInfoResult;
        }
        FileInfo fileInfo = fileInfoResult.getDate();
        log.info("文件名:" + fileInfo.getRealName() + "." + fileInfo.getSuffix());
        if (!Tool.checkSupportImageType(fileInfo.getSuffix())){
            log.error("==========文件类型不支持==========");
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
        }
        //安全检查
        ServerResponse<Image> imageResult = decodeImage(multipartFile);
        if (!imageResult.isSuccess()){
            return imageResult;
        }
        log.info("安全检查:PASS");
        //渲染处理
        ServerResponse<BufferedImage> buffImgResult = securityProcessor(imageResult.getDate());
        if (!buffImgResult.isSuccess()){
            return buffImgResult;
        }
        log.info("图片渲染:PASS");
        //文件存储
        return storageImage(buffImgResult.getDate(),fileInfo,courseId);

    }

    @Override
    public ServerResponse<FileInfo> parserFileInfo(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        if (filename == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
        }
        int suffixIndex = filename.lastIndexOf('.');
        if (suffixIndex <= 0){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
        }
        String suffix = filename.substring(suffixIndex + 1);
        String prefix = filename.substring(0,suffixIndex);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setRealName(prefix);
        fileInfo.setSuffix(suffix);
        return ServerResponse.createBySuccess(fileInfo);
    }

    @Override
    public ServerResponse<Image> decodeImage(MultipartFile multipartFile) {
        Image image;
        try {
            image = ImageIO.read(multipartFile.getInputStream());
            if (image == null || image.getHeight(null) <= 0 || image.getWidth(null) <= 0){
                return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
            }
        }catch (Exception e){
            log.error("==========文件读取失败==========",e);
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"不支持的文件类型");
        }
        return ServerResponse.createBySuccess(image);
    }

    @Override
    public ServerResponse<BufferedImage> securityProcessor(Image image) {
        try{
            BufferedImage buffImg = new BufferedImage(
                    image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            //锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(
                    image.getScaledInstance(image.getWidth(null), image.getHeight(null), Image.SCALE_SMOOTH),
                    0, 0, null);
            //设置颜色
            g.setColor(Color.BLACK);
            //设置字体
            g.setFont(new Font("宋体", Font.BOLD, 30));
            //设置透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0));
            //绘制
            g.drawString("Cloud Class",image.getWidth(null) / 2,image.getHeight(null) / 2);
            g.dispose();
            return ServerResponse.createBySuccess(buffImg);
        }catch (Exception e){
            log.error("==========二次渲染失败==========",e);
            return ServerResponse.createByError("图片渲染失败");
        }
    }

    @Override
    public ServerResponse storageImage(BufferedImage buffImg,FileInfo fileInfo,String courseId) {
        fileInfo.setId(Tool.uuid());
        File file = new File("/usr/cloudclass/files/" + courseId + "/image/" + fileInfo.getId() + "." + fileInfo.getSuffix());
        File dir = file.getParentFile();
        //检查父文件夹
        if (!dir.exists()){
            if (!dir.mkdirs()){
                log.error("==========文件夹创建失败==========");
                return ServerResponse.createByError("上传失败");
            }
        }
        //检查文件冲突
        if (file.exists()){
            log.error("==========文件冲突==========");
            return ServerResponse.createByError("上传失败");
        }
        //存储文件
        try {
            if (file.createNewFile()) {
                OutputStream os = new FileOutputStream(file);
                ImageIO.write(buffImg, fileInfo.getSuffix(), os);
                fileMapper.insert(fileInfo);
                log.info("==========图片上传结束==========");
                return ServerResponse.createBySuccessMsg("上传成功");
            }
        } catch (Exception e) {
            log.error("==========文件存储失败==========", e);
        }
        return ServerResponse.createByError("上传失败");
    }
}
