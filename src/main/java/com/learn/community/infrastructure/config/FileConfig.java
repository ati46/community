package com.learn.community.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwt
 * @Title 文件配置类
 * @Description
 * @date 2020/7/29下午2:24
 */
@Getter
@Configuration
public class FileConfig {
    @Value("${file.rootPath}")
    private String rootPath;

    @Value("${file.urlPath}")
    private String urlPath;
}
