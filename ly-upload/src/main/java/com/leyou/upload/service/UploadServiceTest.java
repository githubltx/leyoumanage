package com.leyou.upload.service;

import com.leyou.upload.web.UploadController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/7/25 - 07 - 25 - 14:09
 * @Description: com.leyou.upload.service
 * @version: 1.0
 */
@Service
public class UploadServiceTest {


    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    // 支持的文件类型
    //      -image/png
    //      -image/jpeg
    //      -image/gif
    //      -image/jpg
    private static final List<String> suffixes =
            Arrays.asList("image/png", "image/jpeg","image/gif","image/jpg");

    public String upload(MultipartFile file) {
        try {
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                logger.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                logger.info("上传失败，文件内容不符合要求");
                return null;
            }
            // 2、保存图片
            // 2.1、生成保存目录
            File dir = new File("C:\\Users\\ltx\\Pictures\\image");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 2.2、保存图片
            file.transferTo(new File(dir, file.getOriginalFilename()));

            // 2.3、拼接图片地址
            String url = "C:\\Users\\ltx\\Pictures\\image\\" + file.getOriginalFilename();

            return url;
        } catch (Exception e) {
            return null;
        }
    }
}
