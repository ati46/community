package com.learn.community.infrastructure.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lwt
 * @Title 返回类
 * @Description 统一返回体
 * @date 2020/7/11下午12:29
 */
@Data
@AllArgsConstructor
public class ResponseResult implements Serializable {

    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;
}
