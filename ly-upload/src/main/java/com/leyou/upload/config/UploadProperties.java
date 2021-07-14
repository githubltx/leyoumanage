package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/7/11 - 07 - 11 - 19:40
 * @Description: com.leyou.upload.config
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
