package com.learn.community.application.impl;

import com.learn.community.application.MenusService;
import com.learn.community.application.UsersService;
import com.learn.community.domain.bean.mysql.Menus;
import com.learn.community.domain.dao.mysql.mapper.MenusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lwt
 * @Title 菜单
 * @Description 菜单实现类
 * @date 2020/7/9下午3:06
 */
@Service
public class MenusServiceImpl implements MenusService {

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private UsersService usersService;

    @Override
    public int insert(Menus menus) {
        return menusMapper.insert(menus);
    }

    @Override
    public int deleteByPrimaryKey(int id) {
        return menusMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Menus menus) {
        menus.setUpdateTime(System.currentTimeMillis());
        return menusMapper.updateByPrimaryKey(menus);
    }

    @Override
    public Menus selectByPrimaryKey(int id) {
        return menusMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Menus> selectAll(int visible) {
        return menusMapper.selectAll(visible);
    }
}
