package com.leyou.upload.web;

import com.leyou.upload.service.UploadService;
import com.leyou.upload.service.UploadServiceTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: litaixu
 * @Date: 2021/7/10 - 07 - 10 - 11:11
 * @Description: com.leyou.upload.web
 * @version: 1.0
 */

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @Autowired
    UploadServiceTest uploadServiceTest;

    /**
     * 上传图片功能
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // 获得request对象,response对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        response.reset();
        String url = this.uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            // url为空，证明上传失败
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 返回200，并且携带url路径
        return ResponseEntity.ok(url);
//        String url = this.uploadServiceTest.upload(file);
//        if (StringUtils.isBlank(url)) {
//            // url为空，证明上传失败
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        // 返回200，并且携带url路径
//        return ResponseEntity.ok(url);


    }
}

