package com.learn.community.domain.dao.es.repo.impl;

import com.alibaba.fastjson.JSON;
import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.dao.es.repo.ESDetailRepo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lwt
 * @Title
 * @Description
 * @date 2020/7/27上午11:22
 */
@Component
public class ESDetailRepoImpl implements ESDetailRepo {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void insert(ESDetail esDetail) {
        // IndexRequest
        IndexRequest indexRequest = new IndexRequest("detail");
        Long id = System.currentTimeMillis();
        esDetail.setId(id);
        String source = JSON.toJSONString(esDetail);
        indexRequest.id(id.toString()).source(source, XContentType.JSON);
        // 操作ES
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
