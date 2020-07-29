package com.learn.community.application;

import com.learn.community.controller.param.ArticleContentParam;
import com.learn.community.domain.bean.mysql.ArticleContents;

import java.util.List;

/**
 * @author lwt
 * @Title 文章内容接口
 * @Description
 * @date 2020/7/12下午3:56
 */
public interface ArticleContentsService {

    /**
     * 根据内容编号获取文章信息
     * @param id 内容编号
     * @return 文章信息
     */
    ArticleContents selectByContentId(int id);

    /**
     * 根据文件夹编号查询所有文章信息
     * @param id 文件夹编号
     * @return 文章信息列表
     */
    List<ArticleContents> selectByFolderId(int id);

    /**
     * 查询所有文章信息
     * @return 文章信息列表
     */
    List<ArticleContents> selectAll();

    /**
     * 新增文章
     * @param articleContentParam 文章参数
     * @return
     */
    int insertArticle(ArticleContentParam articleContentParam);

    /**
     * 根据分页查询文章信息
     * @param pageStart 开始位置
     * @param pageSize 每页数量
     * @return 文章信息列表
     */
    List<ArticleContents> selectAllByPage(int pageStart, int pageSize);

    /**
     * 查询前几个文章内容
     * @return 文章信息列表
     */
    List<ArticleContents> selectTop();
}
