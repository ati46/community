package com.learn.community.infrastructure.security;

import com.learn.community.domain.bean.mysql.Users;
import com.learn.community.domain.dao.mysql.mapper.UsersMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("securityUserService")
public class SecurityUserService implements UserDetailsService {
    @Resource
    private UsersMapper usersMapper;
//    @Resource
//    private RoleMapper roleMapper;
//    @Resource
//    private MenuRightMapper menuRightMapper;
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        Users userDTO = usersMapper.selectByLoginName(loginName);
        // 默认用户ID为1的为管理员
        if (null != userDTO){
//            if(1L == userDTO.getId()) {
//                this.getAdminPermission(userDTO);
//            }
            SecurityUser securityUser = new SecurityUser(userDTO);
            return securityUser;
        } else {
            throw new UsernameNotFoundException(loginName + " 用户不存在!");
        }
    }

    /**
     * 为管理员赋所有权限
     * @param userDTO
     * @return
     */
//    private UserDTO getAdminPermission(UserDTO userDTO) {
//        List<Role> roles = roleMapper.selectAll();
//        List<MenuRight> menuRights = menuRightMapper.selectAll();
//        userDTO.setRoles(roles);
//        userDTO.setMenus(menuRights);
//        return userDTO;
//    }
}
