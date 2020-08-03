package com.learn.community.domain.dao.es.repo.impl;

import com.alibaba.fastjson.JSON;
import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.dao.es.repo.ESDetailRepo;
import com.learn.community.infrastructure.common.EsConstant;
import com.learn.community.infrastructure.utils.BeanUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        IndexRequest indexRequest = new IndexRequest(EsConstant.DETAIL);
        //Long id = System.currentTimeMillis();
        int id = esDetail.getContentId();
        esDetail.setId(id);
        String source = JSON.toJSONString(esDetail);
        indexRequest.id(String.valueOf(id)).source(source, XContentType.JSON);
        // 操作ES
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContent(ESDetail esDetail) {
        UpdateRequest updateRequest = new UpdateRequest(EsConstant.DETAIL, String.valueOf(esDetail.getContentId()));
        try {
            esDetail.setUpdateTime(System.currentTimeMillis());
            Map<String, Object> map = BeanUtils.bean2map(esDetail);
            updateRequest.doc(map);
            UpdateResponse result = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
