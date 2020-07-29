package com.learn.community.domain.dao.mongodb.repo.impl;

import com.learn.community.domain.bean.mongodb.Detail;
import com.learn.community.domain.dao.mongodb.repo.DetailRepo;
import com.learn.community.infrastructure.utils.MongodbUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lwt
 * @Title 内容表数据操作实现
 * @Description
 * @date 2020/7/23下午1:38
 */
@Component
public class DetailRepoImpl implements DetailRepo {

    @Override
    public int insert(Detail detail) {
        MongodbUtils.save(detail, "Detail");
        return 1;
    }

    @Override
    public int updateFolder(Detail detail) {
        String[] updateKeys = {"folderName","updateTime"};
        Object[] updateValues = {detail.getFolderName(), System.currentTimeMillis()};
        MongodbUtils.insertOrUpdate("folderId", detail.getFolderId(), updateKeys, updateValues, "Detail");
        return 1;
    }

    @Override
    public int updateArticle(Detail detail) {
        String[] updateKeys = {"articleName","updateTime"};
        Object[] updateValues = {detail.getArticleName(), System.currentTimeMillis()};
        MongodbUtils.insertOrUpdate("articleId", detail.getArticleId(), updateKeys, updateValues, "Detail");
        return 1;
    }

    @Override
    public int updateContent(Detail detail) {
        String[] updateKeys = {"markContent", "htmlContent","updateTime"};
        Object[] updateValues = {detail.getMarkContent(), detail.getHtmlContent(), System.currentTimeMillis()};
        MongodbUtils.insertOrUpdate("contentId", detail.getContentId(), updateKeys, updateValues, "Detail");
        return 1;
    }

    @Override
    public int delete(Detail detail) {
        return 0;
    }

    @Override
    public int insertOrUpdate(Detail detail) {
        String[] updateKeys = {"folderName", "articleName", "markContent", "htmlContent", "tag", "updateTime"};
        Object[] updateValues = {detail.getFolderName(), detail.getArticleName(), detail.getMarkContent(), detail.getHtmlContent(), detail.getTag(), System.currentTimeMillis()};
        MongodbUtils.insertOrUpdate("contentId", detail.getContentId(), updateKeys, updateValues, "Detail");
        return 1;
    }

    @Override
    public Detail selectByContentId(int id) {
        String[] findKeys = {"contentId"};
        Object[] findValues = {id};
        return (Detail) MongodbUtils.findOne(new Detail(), findKeys, findValues);
    }

    @Override
    public List<Detail> selectByFolderId(int id) {
        String[] findKeys = {"folderId"};
        Object[] findValues = {id};
        return (List<Detail>) MongodbUtils.find(new Detail(), findKeys, findValues);
    }

    @Override
    public List<Detail> selectAll() {
        return (List<Detail>) MongodbUtils.findAll(new Detail());
    }

    @Override
    public List<Detail> selectAllByPage(int pageStart, int pageSize) {
        return (List<Detail>) MongodbUtils.page(new Detail(), null, null, pageStart, pageSize);
    }

    @Override
    public List<Detail> selectTop() {
        return (List<Detail>) MongodbUtils.page(new Detail(), null, null, 0, 4);
    }

    @Override
    public List<Detail> search(String searchContent, int pageStart, int pageSize) {
        String[] searchKeys = searchContent.split(" ");
        return (List<Detail>) MongodbUtils.search(new Detail(), searchKeys, pageStart, pageSize);
    }
}
