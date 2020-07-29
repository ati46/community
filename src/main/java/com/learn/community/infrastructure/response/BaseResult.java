package com.learn.community.infrastructure.response;

import java.util.Map;

/**
 * @author lwt
 * @Title 返回类
 * @Description 统一返回体
 * @date 2020/7/11下午12:51
 */
public class BaseResult {
    /**
     * 返回状态码
     */
    public static final String CODE = "code";
    /**
     * 返回信息
     */
    public static final String MSG = "msg";
    /**
     * 数据
     */
    public static final String DATA = "data";

    public static Map<String, Object> success(Map<String, Object> map, Object data) {
        map.put(CODE, ResponseCode.SUCCESS.getCode());
        map.put(MSG, ResponseCode.SUCCESS.getMsg());
        map.put(DATA, data);
        return map;
    }
}
