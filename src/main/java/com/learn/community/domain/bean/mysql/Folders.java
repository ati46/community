package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Folders {
    /**
     * 编号
     */
    private int id;
    /**
     * 文件夹名字
     */
    private String title;
    /**
     * 所属菜单编号
     */
    private int menuId;
    /**
     * 状态
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
