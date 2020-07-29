package com.learn.community.application;

import com.learn.community.domain.bean.mysql.Articles;

import java.util.List;

/**
 * @author lwt
 * @Title 文章
 * @Description 文章接口类
 * @date 2020/7/9下午3:02
 */
public interface ArticlesService {

    int insert(Articles articles);

    int deleteByPrimaryKey(int id);

    int updateByPrimaryKey(Articles articles);

    Articles selectByPrimaryKey(int id);

    List<Articles> selectByFolderId(int id);

    List<Articles> selectAll();
}
