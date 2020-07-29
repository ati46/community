package com.learn.community.application;

import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.bean.mongodb.Detail;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;

/**
 * @author lwt
 * @Title ESDetail接口类
 * @Description
 * @date 2020/7/27上午10:49
 */
public interface ESDetailService {

    ESDetail selectByContentId(int id);

    List<ESDetail> selectByFolderId(int id);

    List<ESDetail> selectAll();

    List<ESDetail> selectAllByPage(int pageStart, int pageSize);

    List<ESDetail> selectTop();

    List<ESDetail> search(String searchContent, int pageStart, int pageSize);

    void insert(ESDetail esDetail);

    boolean isExistsIndex(String idxName);

    void createIndex(String idxName, String idxSql);

    <T> List<T> searcht(String idxName, SearchSourceBuilder builder, Class<T> c);

    ESDetail selectByArticleId(int id);
    
}
