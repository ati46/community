package com.learn.community.application;

import com.learn.community.domain.bean.mongodb.Detail;

import java.util.List;

/**
 * @author lwt
 * @Title 文章内容接口
 * @Description
 * @date 2020/7/12下午3:56
 */
public interface DetailService {

    Detail selectByContentId(int id);

    List<Detail> selectByFolderId(int id);

    List<Detail> selectAll();

    List<Detail> selectAllByPage(int pageStart, int pageSize);

    List<Detail> selectTop();

    List<Detail> search(String searchContent, int pageStart, int pageSize);
}
