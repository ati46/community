package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 权限表
 * @Description 权限表
 * @date 2020/7/13下午4:38
 */
@Setter
@Getter
public class Roles {
    /**
     * 编号
     */
    private int id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色说明
     */
    private String remark;
    /**
     * 状态
     *
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
