package com.learn.community.controller;

import com.learn.community.application.ArticleContentsService;
import com.learn.community.application.DetailService;
import com.learn.community.domain.bean.mongodb.Detail;
import com.learn.community.domain.bean.mysql.ArticleContents;
import com.learn.community.infrastructure.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lwt
 * @Title 文章内容接口
 * @Description
 * @date 2020/7/12下午4:00
 */
@BaseResponse
@RestController
@RequestMapping("/acs")
public class ArticleContentsController {

    @Autowired
    private ArticleContentsService articleContentsService;

    @Autowired
    private DetailService detailService;

    @GetMapping("/content/{id}")
    public Detail content(@PathVariable int id) {
//        ArticleContents contents = articleContentsService.selectByContentId(id);
//        return contents;
        Detail detail = detailService.selectByContentId(id);
        return detail;
    }

    @GetMapping("/folder/{id}")
    public List<Detail> folder(@PathVariable int id) {
//        List<ArticleContents> list = articleContentsService.selectByFolderId(id);
//        return list;
        List<Detail> list = detailService.selectByFolderId(id);
        return list;
    }

    @GetMapping("")
    public List<Detail> findAll() {
//        List<ArticleContents> list = articleContentsService.selectAll();
//        return list;
        List<Detail> list = detailService.selectAll();
        return list;
    }

    @GetMapping("/page")
    public List<Detail> findAllByPage(@RequestParam int pageNum) {
        int pageSize = 6;
        int pageStart = (pageNum - 1) * pageSize;
        int pageEnd = pageStart + pageSize;
        List<Detail> list = detailService.selectAllByPage(pageStart, pageSize);
        return list;
    }

    @GetMapping("/page/search")
    public List<Detail> findAllByPageSearch(@RequestParam String searchText, @RequestParam int pageNum) {
        int pageSize = 6;
        int pageStart = (pageNum - 1) * pageSize;
        int pageEnd = pageStart + pageSize;
        List<Detail> list = detailService.search(searchText, pageStart, pageSize);
        return list;
    }

    @GetMapping("/top")
    public List<Detail> findTop() {
        List<Detail> list = detailService.selectTop();
        return list;
    }
}
