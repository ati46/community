package com.learn.community.controller;

import com.alibaba.fastjson.JSON;
import com.learn.community.application.ESDetailService;
import com.learn.community.controller.vo.IdxVo;
import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.infrastructure.response.BaseResponse;
import com.learn.community.infrastructure.utils.ElasticUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwt
 * @Title es接口类
 * @Description
 * @date 2020/7/27上午10:32
 */

@Slf4j
@BaseResponse
@RestController
@RequestMapping("/es")
public class ESDetailController {

    @Autowired
    private ESDetailService esDetailService;

    @GetMapping("/page")
    public List<ESDetail> findAllByPage(@RequestParam int pageNum) {
        int pageSize = 6;
        int pageStart = (pageNum - 1) * pageSize;
        int pageEnd = pageStart + pageSize;
        List<ESDetail> list = esDetailService.selectAllByPage(pageStart, pageSize);
        return list;
    }

    @GetMapping("/page/search")
    public List<ESDetail> findAllByPageSearch(@RequestParam String searchText, @RequestParam int pageNum) {
        int pageSize = 6;
        int pageStart = (pageNum - 1) * pageSize;
        int pageEnd = pageStart + pageSize;
        List<ESDetail> list = esDetailService.search(searchText, pageStart, pageSize);
        return list;
    }

    @GetMapping("/top")
    public List<ESDetail> findTop() {
        List<ESDetail> list = esDetailService.selectTop();
        return list;
    }

    @GetMapping("/index")
    public boolean createIndex() {
        try {
            Map<String, Map<String, Object>> properties = new HashMap<>();
            Map<String, Object> id = new HashMap<>();
            id.put("type", "long");

            Map<String, Object> markContent = new HashMap<>();
            markContent.put("type", "text");
            markContent.put("index", true);
            markContent.put("analyzer", "ik_max_word");
            markContent.put("search_analyzer", "ik_max_word");

            Map<String, Object> htmlContent = new HashMap<>();
            markContent.put("type", "text");
            markContent.put("index", true);
            markContent.put("analyzer", "ik_max_word");
            markContent.put("search_analyzer", "ik_max_word");

            Map<String, Object> articleName = new HashMap<>();
            articleName.put("type", "text");
            articleName.put("index", true);
            articleName.put("analyzer", "ik_max_word");
            markContent.put("search_analyzer", "ik_max_word");

            Map<String, Object> folderName = new HashMap<>();
            folderName.put("type", "text");
            folderName.put("index", true);
            folderName.put("analyzer", "ik_max_word");
            markContent.put("search_analyzer", "ik_max_word");

            Map<String, Object> tag = new HashMap<>();
            tag.put("type", "text");
            tag.put("index", true);
            tag.put("analyzer", "ik_max_word");
            markContent.put("search_analyzer", "ik_smart");

            properties.put("id", id);
            properties.put("markContent", markContent);
            properties.put("htmlContent", htmlContent);
            properties.put("articleName", articleName);
            properties.put("folderName", folderName);
            properties.put("tag", tag);

            IdxVo.IdxSql idxSql = new IdxVo.IdxSql();
            idxSql.setDynamic(false);
            idxSql.setProperties(properties);
            IdxVo idxVo = new IdxVo();
            idxVo.setIdxName("detail");
            idxVo.setIdxSql(idxSql);
            //索引不存在，再创建，否则不允许创建
            if (!esDetailService.isExistsIndex(idxVo.getIdxName())) {
                String idx = JSON.toJSONString(idxVo.getIdxSql());
                log.warn(" idxName={}, idxSql={}", idxVo.getIdxName(), idx);
                esDetailService.createIndex(idxVo.getIdxName(), idx);
                return true;
            } else {
                log.error("索引已经存在，不允许创建");
                return false;
            }
        } catch (Exception e) {
            log.error("创建索引出错：", e);
            return false;
        }
    }

    @GetMapping("/get")
    public List<?> get() {

        try {
            //Class<?> clazz = Class.forName("ESDetail");
            MatchQueryBuilder queryBuilders=null;

            queryBuilders = QueryBuilders.matchQuery("markContent", "厉害");
            queryBuilders = QueryBuilders.matchQuery("markContent", "我的");
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("markContent", "厉害").operator(Operator.OR);
            if(null!=queryBuilders){
                SearchSourceBuilder searchSourceBuilder = ElasticUtil.initSearchSourceBuilder(multiMatchQueryBuilder);
                List<?> data = esDetailService.searcht("detail",searchSourceBuilder,ESDetail.class);
                return data;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
