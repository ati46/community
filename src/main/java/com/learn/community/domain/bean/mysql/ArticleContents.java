package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 文章内容表
 * @Description 关联查询文章内容
 * @date 2020/7/12下午3:45
 */
@Getter
@Setter
public class ArticleContents {
    /**
     * 内容编号
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 文件夹名称
     */
    private String folderName;
    /**
     * 查看次数
     */
    private int watchNum;
    /**
     * 评论次数
     */
    private int commentNum;
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
}
