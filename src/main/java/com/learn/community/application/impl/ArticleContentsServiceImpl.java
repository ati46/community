package com.learn.community.application.impl;

import com.learn.community.application.ArticleContentsService;
import com.learn.community.application.ArticlesService;
import com.learn.community.application.ContentsService;
import com.learn.community.controller.param.ArticleContentParam;
import com.learn.community.domain.bean.mysql.ArticleContents;
import com.learn.community.domain.bean.mysql.Articles;
import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.domain.dao.mysql.mapper.ArticleContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lwt
 * @Title 文章内容接口实现类
 * @Description
 * @date 2020/7/12下午3:58
 */
@Service
public class ArticleContentsServiceImpl implements ArticleContentsService {

    @Autowired
    private ArticleContentsMapper articleContentsMapper;

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private ContentsService contentsService;

    @Override
    public ArticleContents selectByContentId(int id) {
        return articleContentsMapper.selectByContentId(id);
    }

    @Override
    public List<ArticleContents> selectByFolderId(int id) {
        return articleContentsMapper.selectByFolderId(id);
    }

    @Override
    public List<ArticleContents> selectAll() {
        return articleContentsMapper.selectAll();
    }

    @Override
    @Transactional
    public int insertArticle(ArticleContentParam articleContentParam) {
        Articles articles = new Articles();
        articles.setTitle(articleContentParam.getTitle());
        articles.setSubTitle("");
        articles.setCommentNum(0);
        articles.setWatchNum(0);
        articles.setFolderId(articleContentParam.getFolderId());
        articles.setCreateTime(System.currentTimeMillis());
        articles.setUpdateTime(System.currentTimeMillis());
        int re = articlesService.insert(articles);
        Contents contents = new Contents();
        contents.setArticleId(articles.getId());
        //TODO 默认tag和用户都是admin，后续需要改掉前端传入
        contents.setAuthor("admin");
        contents.setAuthorId(1);
        contents.setHtmlContent(articleContentParam.getHtmlContent());
        contents.setMarkContent(articleContentParam.getMarkContent());
        contents.setTag("java");
        contents.setCreateTime(System.currentTimeMillis());
        contents.setUpdateTime(System.currentTimeMillis());
        int result = contentsService.insert(contents);
        return result;
    }

    @Override
    public List<ArticleContents> selectAllByPage(int pageStart, int pageSize) {
        return articleContentsMapper.selectAllByPage(pageStart, pageSize);
    }

    @Override
    public List<ArticleContents> selectTop() {
        return articleContentsMapper.selectTop();
    }

}
