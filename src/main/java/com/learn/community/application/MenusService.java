package com.learn.community.application;

import com.learn.community.domain.bean.mysql.Menus;

import java.util.List;

/**
 * @author lwt
 * @Title 菜单
 * @Description 菜单接口类
 * @date 2020/7/9下午3:00
 */
public interface MenusService {

    int insert(Menus menus);

    int deleteByPrimaryKey(int id);

    int updateByPrimaryKey(Menus menus);

    Menus selectByPrimaryKey(int id);

    List<Menus> selectAll(int visible);
}
