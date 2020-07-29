package com.learn.community.infrastructure.common;

/**
 * @author lwt
 * @Title 全局常量
 * @Description
 * @date 2020/7/14上午10:15
 */
public class SystemConstant {
    public static final String JWT_SECERT = "atitjy";

    public static final long JWT_TIMEOUT = 2 * 60 * 60 * 1000;

    public static final Long JWT_REMEMBER = 604800000L;

    /**
     * 时间格式化
     */
    public static final String DATE_FORMATTER_TIME = "YYYY-MM-dd HH:mm:ss";
    public static final String DATE_FORMATTER_DATE = "YYYY-MM-dd";

    /**
     * 公共状态： 启用、未启用
     */
    public static final int STATUS_ENABLE = 1;
    public static final int STATUS_DISABLE = 0;

}
