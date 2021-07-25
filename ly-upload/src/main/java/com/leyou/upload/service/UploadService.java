package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import com.leyou.upload.web.UploadController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private UploadProperties uploadProperties;
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    // 支持的文件类型
   // private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg","image/gif");

    public String upload(MultipartFile file) {
        try {
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType();
            if (!uploadProperties.getAllowTypes().contains(type)) {
                logger.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                logger.info("上传失败，文件内容不符合要求");
                return null;
            }
//            // 2、保存图片
//            // 2.1、生成保存目录
//            File dir = new File("D:\\gitSpringboot\\uploadresources");
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            // 2.2、保存图片
//            file.transferTo(new File(dir, file.getOriginalFilename()));
            //上传到fastdfs
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            //extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            final StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 2.3、拼接图片地址
            String url = uploadProperties.getBaseUrl() + storePath.getFullPath();

            return url;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.UPLOAD_ERROR);
        }
    }
}