package com.learn.community.application;

import com.learn.community.controller.param.LoginParam;
import com.learn.community.controller.vo.UserVO;
import com.learn.community.domain.bean.mysql.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author lwt
 * @Title 用户业务接口
 * @Description 用户业务处理
 * @date 2020/7/13下午5:02
 */
public interface UsersService {

    UserVO login(LoginParam loginParam);

    Users selectByPrimaryKey(int id);

    List<Users> selectAll();

    boolean logout(LoginParam loginParam);

    boolean isLogin();

    Users getLoginUser();
}
