package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menus {
    /**
     * 编号
     */
    private int id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单链接
     */
    private String url;
    /**
     * 所属用户编号
     */
    private int userId;
    /**
     * 是否是私有的
     */
    private int visible;
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
