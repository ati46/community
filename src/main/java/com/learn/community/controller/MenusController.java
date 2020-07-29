package com.learn.community.controller;

import com.alibaba.fastjson.JSON;
import com.learn.community.application.MenusService;
import com.learn.community.application.UsersService;
import com.learn.community.domain.bean.mysql.Menus;
import com.learn.community.infrastructure.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwt
 * @Title 菜单接口类
 * @Description 提供菜单url
 * @date 2020/7/9下午3:08
 */

@BaseResponse
@RestController
@RequestMapping("/menu")
public class MenusController {
    @Autowired
    private MenusService menusService;

    @Autowired
    private UsersService usersService;

    @PostMapping("")
    public String addMenu(@RequestBody Menus menus) {
        int result = menusService.insert(menus);
        return result + "";
    }

    @PutMapping("")
    public int modifyMenu(@RequestBody Menus menus) {
        int result = menusService.updateByPrimaryKey(menus);
        return result;
    }

    @GetMapping("/{id}")
    public String find(@PathVariable int id) {
        Menus menus = menusService.selectByPrimaryKey(id);
        return JSON.toJSONString(menus);
    }

    @GetMapping("")
    public Map<String, Object> findAll() {
        boolean status = usersService.isLogin();
        int visible = status ? 0 : 1;
        List<Menus> list = menusService.selectAll(visible);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", list.size());
        map.put("status", status);
        return map;
    }
}
