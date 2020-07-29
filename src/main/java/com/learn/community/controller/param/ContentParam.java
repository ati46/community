package com.learn.community.controller.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 保存文章参数类
 * @Description 文章参数
 * @date 2020/7/23上午11:21
 */
@Getter
@Setter
public class ContentParam {
    /**
     * 编号
     */
    private int id;
    /**
     * 文章编号
     */
    private int articleId;
    /**
     * 标签
     */
    private String tag;
    /**
     * html内容
     */
    private String htmlContent;
    /**
     * markdown内容
     */
    private String markContent;
}
