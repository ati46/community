package com.learn.community.infrastructure.common;

/**
 * @author lwt
 * @Title redisKey
 * @Description 用于定义缓存key格式
 * @date 2020/7/14上午10:37
 */
public class RedisKey {

    /**
     * 用户缓存模块
     */
    public interface User{
        String USER = "User:";
        String TOKEN = USER + "Token:%s";
        String REFRESH_TOKEN = USER + "Refresh:Token:%s";
    }
}
