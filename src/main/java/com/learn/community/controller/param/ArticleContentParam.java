package com.learn.community.controller.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 文章内容参数类
 * @Description 保存文章用的参数
 * @date 2020/7/12下午2:26
 */
@Getter
@Setter
public class ArticleContentParam {

    private String title;
    private int folderId;
    private String htmlContent;
    private String markContent;
}
