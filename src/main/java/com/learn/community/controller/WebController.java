package com.learn.community.controller;

import com.learn.community.application.ContentsService;
import com.learn.community.application.FoldersService;
import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.domain.bean.mysql.Folders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lwt
 * @Title 页面
 * @Description 统一管理页面
 * @date 2020/7/11下午4:28
 */
@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private FoldersService foldersService;

    @Autowired
    private ContentsService contentsService;

    @GetMapping("/folder/{id}")
    public String find(@PathVariable int id, Model model) {
        List<Folders> folders = foldersService.selectByMenuId(id);
        model.addAttribute("folders", folders);
        return "folder";
    }

    @GetMapping("/content/all")
    public String findAll() {
        List<Contents> list = contentsService.selectAll();
        return "content";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable int id, Model model) {
        //TODO 隐藏的文章不能给show
        Contents contents = contentsService.selectByPrimaryKey(id);
        model.addAttribute("htmlContent", contents.getHtmlContent());
        return "show";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
