package com.learn.community.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lwt
 * @Title 用户返回数据
 * @Description
 * @date 2020/7/14上午10:10
 */
@Setter
@Getter
public class UserVO {

    private String loginName;
    private String name;
    private String photo;
    private String token;
    private String refreshToken;

}
