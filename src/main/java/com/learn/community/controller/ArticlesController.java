package com.learn.community.controller;

import com.learn.community.application.ArticleContentsService;
import com.learn.community.application.ArticlesService;
import com.learn.community.controller.assembler.ArticleAssembler;
import com.learn.community.controller.param.ArticleContentParam;
import com.learn.community.controller.vo.ArticleVO;
import com.learn.community.domain.bean.mysql.Articles;
import com.learn.community.infrastructure.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwt
 * @Title 文章
 * @Description 提供文章的接口
 * @date 2020/7/9下午3:11
 */
@BaseResponse
@RestController
@RequestMapping("/article")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private ArticleContentsService articleContentsService;

    @PostMapping("/content")
    public int addContent(@RequestBody ArticleContentParam articleContentParam) {
        int result = articleContentsService.insertArticle(articleContentParam);
        return result;
    }

    @PostMapping("")
    public ArticleVO addArticles(@RequestBody ArticleContentParam articleContentParam) {
        Articles articles = new Articles();
        articles.setTitle(articleContentParam.getTitle());
        articles.setSubTitle("");
        articles.setCommentNum(0);
        articles.setWatchNum(0);
        articles.setFolderId(articleContentParam.getFolderId());
        articles.setCreateTime(System.currentTimeMillis());
        articles.setUpdateTime(System.currentTimeMillis());
        int result = articlesService.insert(articles);
        ArticleVO articleVO = ArticleAssembler.articleToArticleVO(articles);
        return articleVO;
    }

    @PutMapping("")
    public int modifyArticles(@RequestBody Articles articles) {
        int result = articlesService.updateByPrimaryKey(articles);
        return result;
    }

    @GetMapping("/{id}")
    public List<Articles> find(@PathVariable int id) {
        List<Articles> articles = articlesService.selectByFolderId(id);
        return articles;
    }

    @GetMapping("")
    public Map<String, Object> findAll() {
        List<Articles> list = articlesService.selectAll();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", list.size());
        return map;
    }

    @GetMapping("/all")
    public Map<String, Object> articleAndContentAll() {
        List<Articles> list = articlesService.selectAll();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", list.size());
        return map;
    }
}
