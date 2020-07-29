package com.learn.community.application.impl;

import com.learn.community.application.ArticlesService;
import com.learn.community.domain.bean.mysql.Articles;
import com.learn.community.domain.dao.mysql.mapper.ArticlesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lwt
 * @Title 文章
 * @Description 文章实现类
 * @date 2020/7/9下午3:04
 */
@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticlesMapper articlesMapper;

    @Override
    public int insert(Articles articles) {
        return articlesMapper.insert(articles);
    }

    @Override
    public int deleteByPrimaryKey(int id) {
        return articlesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Articles articles) {
        return articlesMapper.updateByPrimaryKey(articles);
    }

    @Override
    public Articles selectByPrimaryKey(int id) {
        return articlesMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Articles> selectByFolderId(int id) {
        return articlesMapper.selectByFolderId(id);
    }

    @Override
    public List<Articles> selectAll() {
        return articlesMapper.selectAll();
    }
}
