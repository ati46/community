package com.learn.community.controller.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lwt
 * @Title 登录需要的参数
 * @Description
 * @date 2020/7/14上午9:14
 */
@Getter
@Setter
public class LoginParam {

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 记住我
     */
    private Boolean rememberMe;
}
