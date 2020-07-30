package com.learn.community.application.impl;

import com.alibaba.fastjson.JSON;
import com.learn.community.application.ESDetailService;
import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.infrastructure.utils.ElasticUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lwt
 * @Title
 * @Description
 * @date 2020/7/27上午10:50
 */
@Slf4j
@Service
public class ESDetailServiceImpl implements ESDetailService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public ESDetail selectByContentId(int id) {
        return null;
    }

    @Override
    public List<ESDetail> selectByFolderId(int id) {
        return null;
    }

    @Override
    public List<ESDetail> selectAll() {
        return null;
    }

    @Override
    public List<ESDetail> selectAllByPage(int pageStart, int pageSize) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页采用简单的from + size分页，适用数据量小的，了解更多分页方式可自行查阅资料
        searchSourceBuilder.from(pageStart);
        searchSourceBuilder.size(pageSize);
        // 排序，根据ID倒叙
        searchSourceBuilder.sort("updateTime", SortOrder.DESC);
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest("detail");
        searchRequest.source(searchSourceBuilder);
        // 查询ES
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        // 获取总数
        Long total = hits.getTotalHits().value;
        // 遍历封装列表对象
        List<ESDetail> esList = new ArrayList<>();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            esList.add(JSON.parseObject(searchHit.getSourceAsString(), ESDetail.class));
        }
        return esList;
    }

    @Override
    public List<ESDetail> selectTop() {
        return null;
    }

    @Override
    public List<ESDetail> search(String searchContent, int pageStart, int pageSize) {
        //MatchQueryBuilder matchQueryBuilder = null;
        BoolQueryBuilder boolQueryBuilder = null;
        // 查询条件，只有查询关键字不为空才带查询条件
        if (StringUtils.isNoneBlank(searchContent)) {
            // QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchContent, "folderName", "desc");
            //QueryBuilders.matchQuery("markContent",searchContent).operator(Operator.OR);
            boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.should(QueryBuilders.matchQuery("markContent", searchContent));
            boolQueryBuilder.should(QueryBuilders.matchQuery("htmlContent", searchContent));
            boolQueryBuilder.should(QueryBuilders.matchQuery("folderName", searchContent));
            boolQueryBuilder.should(QueryBuilders.matchQuery("articleName", searchContent));
            boolQueryBuilder.should(QueryBuilders.matchQuery("tag", searchContent));
        }
        SearchSourceBuilder searchSourceBuilder = ElasticUtil.initSearchSourceBuilder(boolQueryBuilder, pageStart, pageSize);
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest("detail");
        searchRequest.source(searchSourceBuilder);
        // 查询ES
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException | ElasticsearchStatusException e) {
            log.error("es搜索出错", e);
            return null;
        }
        if (searchResponse != null) {
            return null;
        }
        SearchHits hits = searchResponse.getHits();
        // 获取总数
        Long total = hits.getTotalHits().value;
        // 遍历封装列表对象
        List<ESDetail> esList = new ArrayList<>();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            ESDetail esDetail = JSON.parseObject(searchHit.getSourceAsString(), ESDetail.class);
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            String markContent = getHighLightString("markContent", highlightFields);
            String htmlContent = getHighLightString("htmlContent", highlightFields);
            String folderName = getHighLightString("folderName", highlightFields);
            String articleName = getHighLightString("articleName", highlightFields);
            String tag = getHighLightString("tag", highlightFields);
            if (StringUtils.isNotBlank(tag)) {
                esDetail.setTag(tag);
            }
            if (StringUtils.isNotBlank(articleName)) {
                esDetail.setArticleName(articleName);
            }
            if (StringUtils.isNotBlank(folderName)) {
                esDetail.setFolderName(folderName);
            }
            if (StringUtils.isNotBlank(htmlContent)) {
                esDetail.setHtmlContent(htmlContent);
            }
            if (StringUtils.isNotBlank(markContent)) {
                esDetail.setMarkContent(markContent);
            }
            esList.add(esDetail);
        }
        return esList;
    }

    /**
     * 获取高亮
     *
     * @param str
     * @param highlightFields
     * @return
     */
    private String getHighLightString(String str, Map<String, HighlightField> highlightFields) {
        String result = "";
        if (highlightFields != null) {
            HighlightField highlightField = highlightFields.get(str);
            if (highlightField != null) {
                Text[] fragments = highlightField.getFragments();
                StringBuilder sb = new StringBuilder();
                for (Text text : fragments) {
                    sb.append(text.toString());
                }
                result = sb.toString();
            }
        }
        return result;
    }

    @Override
    public <T> List<T> searcht(String idxName, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ESDetail selectByArticleId(int id) {

        return null;
    }

    @Override
    public void insert(ESDetail esDetail) {

    }

    @Override
    public boolean isExistsIndex(String idxName) {
        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest(idxName),RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void createIndex(String idxName, String idxSql) {
        try {
            if (!this.indexExist(idxName)) {
                log.error(" idxName={} 已经存在,idxSql={}",idxName,idxSql);
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);
            buildSetting(request);
            request.mapping(idxSql, XContentType.JSON);
//            request.settings() 手工指定Setting
            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public boolean indexExist(String idxName) throws Exception {
        GetIndexRequest request = new GetIndexRequest(idxName);
        //TRUE-返回本地信息检索状态，FALSE-还是从主节点检索状态
        request.local(false);
        //是否适应被人可读的格式返回
        request.humanReadable(true);
        //是否为每个索引返回所有默认设置
        request.includeDefaults(false);
        //控制如何解决不可用的索引以及如何扩展通配符表达式,忽略不可用索引的索引选项，仅将通配符扩展为开放索引，并且不允许从通配符表达式解析任何索引
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    public void buildSetting(CreateIndexRequest request){
        request.settings(Settings.builder().put("index.number_of_shards",3)
                .put("index.number_of_replicas",2));
    }
}
