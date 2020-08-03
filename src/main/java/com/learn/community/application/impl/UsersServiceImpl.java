package com.learn.community.application.impl;

import com.learn.community.application.UsersService;
import com.learn.community.controller.param.LoginParam;
import com.learn.community.controller.vo.UserVO;
import com.learn.community.domain.bean.mysql.Users;
import com.learn.community.domain.dao.mysql.mapper.UsersMapper;
import com.learn.community.infrastructure.common.JwtUtils;
import com.learn.community.infrastructure.common.RedisKey;
import com.learn.community.infrastructure.common.RedisUtil;
import com.learn.community.infrastructure.common.SystemConstant;
import com.learn.community.infrastructure.response.BaseException;
import com.learn.community.infrastructure.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lwt
 * @Title 用户业务实现类
 * @Description 实现用户业务
 * @date 2020/7/13下午5:04
 */
@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Value("${server.session.timeout:3000}")
    private Long timeout;

    @Override
    public UserVO login(LoginParam loginParam) {
        Users users = usersMapper.selectByUserInfo(loginParam.getLoginName(), loginParam.getPassWord());
        if (null == users) {
            throw new BaseException(ResponseCode.LOGIN_ERROR);
        }

        UserVO userVO = new UserVO();
        userVO.setName(users.getName());
        userVO.setLoginName(users.getLoginName());
        userVO.setPhoto(users.getPhoto());
        String token = JwtUtils.createJWT(users);
        userVO.setToken(token);

        RedisUtil.StringOps.setEx(String.format(RedisKey.User.TOKEN, users.getLoginName()), token, SystemConstant.JWT_TIMEOUT, TimeUnit.MILLISECONDS);
        return userVO;
    }

    @Override
    public Users selectByPrimaryKey(int id) {
        return null;
    }

    @Override
    public List<Users> selectAll() {
        return null;
    }

    @Override
    public boolean logout(LoginParam loginParam) {
        RedisUtil.KeyOps.delete(String.format(RedisKey.User.TOKEN, loginParam.getLoginName()));
        return false;
    }

    @Override
    public boolean isLogin() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                //TODO 管理员特殊处理
                return true;
            }
        } catch (Exception e) {
            log.error("获取登录状态出错", e);
        }
        return false;
    }


    @Override
    public Users getLoginUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Users users = (Users) authentication.getPrincipal();
                //TODO 管理员特殊处理
                return users;
            }
        } catch (Exception e) {
            log.error("获取登录状态出错", e);
        }
        return null;
    }


}
