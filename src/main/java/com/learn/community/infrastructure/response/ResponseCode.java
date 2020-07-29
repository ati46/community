package com.learn.community.infrastructure.response;

/**
 * 状态返回
 */
public enum ResponseCode {
    /**
     * 成功返回的状态码
     */
    SUCCESS(10000, "success"),
    /**
     * 资源不存在的状态码
     */
    RESOURCES_NOT_EXIST(40004, "资源不存在"),
    /**
     * 所有无法识别的异常默认的返回状态码
     */
    SERVICE_ERROR(50000, "服务器异常"),
    /**
     * 登录失败
     */
    LOGIN_ERROR(20006, "登录失败"),
    /**
     * 用户未找到
     */
    LOGIN_NOT_FOUND(20005, "用户名密码错误"),

    /**
     * 未登录
     */
    LOGIN_NO(20009, "未登录"),
    /**
     * 未授权
     */
    AUTH_LACK(20004, "未授权"),
    /**
     * 认证失败
     */
    AUTH_FAIL(20007, "认证失败"),
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(20000, "登录成功"),
    /**
     * token相关
     */
    TOKEN_EXPIRED(20001, "token 已过期，请重新登录"),

    TOKEN_PARSE_ERROR(20002, "token 解析失败，请重新登录"),

    TOKEN_OUT_OF_CTRL(20003, "当前用户已在别处登录，请尝试更改密码或重新登录！"),
    ;
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}