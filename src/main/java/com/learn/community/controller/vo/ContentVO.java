package com.learn.community.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 文章视图表
 * @Description 用来存放文章需要展示的信息
 * @date 2020/7/16下午3:46
 */
@Setter
@Getter
public class ContentVO {
    /**
     * 编号
     */
    private int id;

    /**
     * 编号
     */
    private int articleId;
    /**
     * html内容
     */
    private String htmlContent;
    /**
     * markdown内容
     */
    private String markContent;

    /**
     * 展示状态 1 编辑状态，0 展示状态
     */
    private int status;

    private String tag;
}
