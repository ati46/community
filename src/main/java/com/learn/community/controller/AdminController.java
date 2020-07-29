package com.learn.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lwt
 * @Title 管理页面
 * @Description 管理员使用的页面
 * @date 2020/7/11上午10:35
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping({"", "/", "/index"})
    public String index(Model model) {

        return "admin/index";
    }

    @RequestMapping("logout")
    public String logout(Model model) {

        return "admin/page/login-1";
    }

    @RequestMapping("login")
    public String login(Model model) {

        return "admin/page/login-2";
    }

    @RequestMapping("table")
    public String table(Model model) {
        return "admin/page/table";
    }
    @RequestMapping("table/add")
    public String tableAdd(Model model) {
        return "admin/page/table/add";
    }
    @RequestMapping("table/edit")
    public String tableEdit(Model model) {
        return "admin/page/table/edit";
    }

    @RequestMapping("menus")
    public String menus(Model model) {
        return "admin/page/menus";
    }
    @RequestMapping("menus/add")
    public String menusAdd(Model model) {
        return "admin/page/menus/add";
    }
    @RequestMapping("menus/edit")
    public String menusEdit(Model model) {
        return "admin/page/menus/edit";
    }

    @RequestMapping("articles")
    public String articles(Model model) {
        return "admin/page/articles";
    }
    @RequestMapping("articles/add")
    public String articlesAdd(Model model) {
        return "admin/page/articles/add";
    }
    @RequestMapping("articles/edit")
    public String articlesEdit(Model model) {
        return "admin/page/articles/edit";
    }

    @RequestMapping("folders")
    public String folders(Model model) {
        return "admin/page/folders";
    }
    @RequestMapping("folders/add")
    public String foldersAdd(Model model) {
        return "admin/page/folders/add";
    }
    @RequestMapping("folders/edit")
    public String foldersEdit(Model model) {
        return "admin/page/folders/edit";
    }

    @RequestMapping("content")
    public String content(Model model) {
        return "admin/page/content";
    }

    @RequestMapping("form")
    public String form(Model model) {
        return "admin/page/form";
    }
}
