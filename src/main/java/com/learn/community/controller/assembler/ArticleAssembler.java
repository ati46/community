package com.learn.community.controller.assembler;

import com.learn.community.controller.vo.ArticleVO;
import com.learn.community.domain.bean.mysql.Articles;

/**
 * @author lwt
 * @Title 文章转型类
 * @Description
 * @date 2020/7/17下午12:04
 */
public class ArticleAssembler {

    public static ArticleVO articleToArticleVO(Articles articles) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(articles.getId());
        articleVO.setTitle(articles.getTitle());
        return articleVO;
    }
}
