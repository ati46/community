package com.learn.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lwt
 * @Title 首页
 * @Description 首页
 * @date 2020/7/9下午4:32
 */
@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/tt")
    public String test(Model model){
        return "test";
    }

    @GetMapping("/markdown")
    public String markdown(Model model){
        return "markdown";
    }
}
