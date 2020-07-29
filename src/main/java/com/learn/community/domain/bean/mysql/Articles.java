package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Articles {
    /**
     * 编号
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
     * 文件夹编号
     */
    private int folderId;
    /**
     * 查看次数
     */
    private int watchNum;
    /**
     * 评论次数
     */
    private int commentNum;
    /**
     * 状态 0 删除 1 未删除
     */
    private int status;
    /**
     * 排序
     */
    private int sort;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;
}
