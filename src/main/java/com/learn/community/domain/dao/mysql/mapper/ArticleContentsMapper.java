package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.ArticleContents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleContentsMapper {

    @Select("select c.id, a.title, a.subTitle, f.title as folderName, a.watchNum, a.commentNum, c.tag, c.htmlContent, c.markContent, c.author from articles as a inner join contents as c on c.articleId = a.id inner join folders as f on a.folderId = f.id where c.id = #{id} where a.status = 1 and f.status = 1 and c.status = 1")
    ArticleContents selectByContentId(int id);

    @Select("select c.id, a.title, a.subTitle, f.title as folderName, a.watchNum, a.commentNum, c.tag, c.htmlContent, c.markContent, c.author from articles as a inner join contents as c on c.articleId = a.id inner join folders as f on a.folderId = f.id where f.Id = #{id} where a.status = 1 and f.status = 1 and c.status = 1")
    List<ArticleContents> selectByFolderId(int id);

    @Select("select c.id, a.title, a.subTitle, f.title as folderName, a.watchNum, a.commentNum, c.tag, c.htmlContent, c.markContent, c.author from articles as a inner join contents as c on c.articleId = a.id inner join folders as f on a.folderId = f.id where a.status = 1 and f.status = 1 and c.status = 1")
    List<ArticleContents> selectAll();

    @Select("select c.id, a.title, a.subTitle, f.title as folderName, a.watchNum, a.commentNum, c.tag, c.htmlContent, c.markContent, c.author from articles as a inner join contents as c on c.articleId = a.id inner join folders as f on a.folderId = f.id where a.status = 1 and f.status = 1 and c.status = 1 limit #{pageStart}, #{pageSize}")
    List<ArticleContents> selectAllByPage(int pageStart, int pageSize);

    @Select("select c.id, a.title, a.subTitle, f.title as folderName, a.watchNum, a.commentNum, c.tag, c.htmlContent, c.markContent, c.author from articles as a inner join contents as c on c.articleId = a.id inner join folders as f on a.folderId = f.id where a.status = 1 and f.status = 1 and c.status = 1 limit 0, 4")
    List<ArticleContents> selectTop();
}
