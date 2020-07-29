package com.learn.community.application;

import com.learn.community.domain.bean.mysql.Contents;

import java.util.List;

/**
 * @author lwt
 * @Title 文章
 * @Description 文章接口类
 * @date 2020/7/9下午3:02
 */
public interface ContentsService {

    int insert(Contents contents);

    int deleteByPrimaryKey(int id);

    int updateByPrimaryKey(Contents contents);

    Contents selectByArticleId(int id);

    Contents selectByPrimaryKey(int id);

    List<Contents> selectAll();

    Contents updateByPrimaryKeyForDetail(Contents contents);
}
