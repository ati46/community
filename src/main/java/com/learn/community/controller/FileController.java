package com.learn.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.community.infrastructure.annotation.AccessLimit;
import com.learn.community.infrastructure.config.FileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author lwt
 * @Title 上传接口类
 * @Description 上传文件
 * @date 2020/7/21上午11:20
 */

@RestController
public class FileController {

    @Autowired
    private FileConfig fileConfig;

    @AccessLimit(limit=1,time=500)
    @PostMapping("/upload")
    public JSONObject upload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", 0);
        jsonObject.put("message", "上传失败");
        jsonObject.put("url", "123132");
        try {
            if (file.isEmpty()) {
                return jsonObject;
            }
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            //String rootPath = request.getSession().getServletContext().getRealPath("/home/lwt/Downloads/images/");
            String rootPath = fileConfig.getRootPath();

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            // 最终文件名
            File dest = new File(rootPath + File.separator + file.getOriginalFilename());

            file.transferTo(dest);
            // 下面response返回的json格式是editor.md所限制的，规范输出就OK

            jsonObject.put("success", 1);
            jsonObject.put("message", "上传成功");
            jsonObject.put("url", fileConfig.getUrlPath() + file.getOriginalFilename());
        } catch (Exception e) {
            jsonObject.put("success", 0);
        }
        return jsonObject;
    }

}
