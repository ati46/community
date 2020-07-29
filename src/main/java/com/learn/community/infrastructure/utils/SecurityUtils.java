package com.learn.community.infrastructure.utils;

import com.learn.community.domain.bean.mysql.Users;
import com.learn.community.infrastructure.security.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    /**
     * 判断当前用户是否已经登陆
     * @return 登陆状态返回 true, 否则返回 false
     */
    public static boolean isLogin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !"anonymousUser".equals(username);
    }
    /**
     * 取得登陆用户的 ID, 如果没有登陆则返回 -1
     * @return 登陆用户的 ID
     */
    public static int getLoginUserId() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (SecurityUtils.isLogin()) {
            SecurityUser userDetails = (SecurityUser) principle;
            return userDetails.getId();
        }
        return -1;
    }
    /**
     *
     * 功能：返回当前用户<br/>
     * @return
     * @exception
     *
     */
    public static Users getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Users)) {
                return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }
        return null;
    }
}
