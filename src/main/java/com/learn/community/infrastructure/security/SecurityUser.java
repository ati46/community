package com.learn.community.infrastructure.security;


import com.learn.community.domain.bean.mysql.Users;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends Users implements UserDetails, Serializable {

    @Setter
    private Boolean accountNonExpired=true;
    @Setter
    private Boolean accountNonLocked=true;
    @Setter
    private Boolean credentialsNonExpired=true;
    @Setter
    private Boolean enabled=true;
    public SecurityUser (Users user) {
        if(user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        List<Roles> userRoles = this.getRoleId();
//        if(userRoles != null){
//            for (Roles role : userRoles) {
//                // Security 内部 需要使用ROLE_前缀来校验
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
//                authorities.add(authority);
//            }
//        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.getRoleId());
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassWord();
    }

    @Override
    public String getUsername() {
        return super.getLoginName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
