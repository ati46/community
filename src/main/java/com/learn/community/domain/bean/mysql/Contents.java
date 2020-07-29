package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 内容表
 * @Description 用来承载文章的内容
 * @date 2020/7/10下午12:09
 */
@Getter
@Setter
public class Contents {
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
    /**
     * 作者
     */
    private String author;
    /**
     * 作者编号
     */
    private int authorId;
    /**
     * 状态
     */
    private int status;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;
}
