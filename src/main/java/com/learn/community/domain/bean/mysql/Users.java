package com.learn.community.domain.bean.mysql;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 用户表
 * @Description 用户数据
 * @date 2020/7/13下午4:36
 */
@Getter
@Setter
public class Users {
    /**
     * 编号
     */
    private int id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 帐号
     */
    private String loginName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 头像地址
     */
    private String photo;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 所属角色编号
     */
    private int roleId;
    /**
     * 最后一次登录时间
     */
    private long lastLoginTime;
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
