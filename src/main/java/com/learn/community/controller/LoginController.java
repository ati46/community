package com.learn.community.controller;

import com.learn.community.application.UsersService;
import com.learn.community.controller.param.LoginParam;
import com.learn.community.controller.vo.UserVO;
import com.learn.community.infrastructure.response.BaseException;
import com.learn.community.infrastructure.response.BaseResponse;
import com.learn.community.infrastructure.response.ResponseCode;
import com.learn.community.infrastructure.security.SecurityUser;
import com.learn.community.infrastructure.utils.CookieUtils;
import com.learn.community.infrastructure.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwt
 * @Title 登录接口
 * @Description 处理用户登录相关的事情
 * @date 2020/7/14上午9:12
 */
@BaseResponse
@RestController
public class LoginController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    public UserVO login(@RequestBody LoginParam loginParam,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginParam.getLoginName(), loginParam.getPassWord()));
        boolean rememberMe = loginParam.getRememberMe() != null;
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        String jwt = jwtUtil.createJWT(authentication, rememberMe, false);
        String jwt_refresh = jwtUtil.createJWT(authentication, rememberMe, true);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("refreshToken", jwt_refresh);
        UserVO userVO = new UserVO();
        userVO.setLoginName(user.getLoginName());
        userVO.setPhoto(user.getPhoto());
        userVO.setName(user.getName());
        userVO.setToken(jwt);
        userVO.setRefreshToken(jwt_refresh);
        CookieUtils.setCookie(response, "token", jwt);
        CookieUtils.setCookie(response, "refreshToken", userVO.getRefreshToken());
        CookieUtils.setCookie(response, "name", userVO.getName());
        CookieUtils.setCookie(response, "photo", userVO.getPhoto());
        CookieUtils.setCookie(response, "loginName", userVO.getLoginName());
        return userVO;
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public boolean logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
        } catch (BaseException e) {
            throw new BaseException(ResponseCode.AUTH_FAIL);
        }
        return true;
    }

    /**
     * 刷新过期的token
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh/token")
    public String refreshToken(String refreshToken) {
        Map<String, String> map;
        try {
            // 刷新
            map = jwtUtil.refreshJWT(refreshToken);
        } catch (BaseException e) {
            throw new BaseException(ResponseCode.TOKEN_EXPIRED);
        }
        return map.get("token");
    }

    @GetMapping("/status")
    public boolean status() {
        boolean status = usersService.isLogin();
        return status;
    }
}
