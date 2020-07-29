package com.learn.community.domain.dao.mongodb.repo;

import com.learn.community.domain.bean.mongodb.Detail;

import java.util.List;

/**
 * @author lwt
 * @Title 内容表数据类
 * @Description
 * @date 2020/7/23下午1:36
 */
public interface DetailRepo {

    int insert(Detail detail);

    int updateFolder(Detail detail);

    int updateArticle(Detail detail);

    int updateContent(Detail detail);

    int delete(Detail detail);

    int insertOrUpdate(Detail detail);

    Detail selectByContentId(int id);

    List<Detail> selectByFolderId(int id);

    List<Detail> selectAll();

    List<Detail> selectAllByPage(int pageStart, int pageSize);

    List<Detail> selectTop();

    List<Detail> search(String searchContent, int pageStart, int pageSize);
}
