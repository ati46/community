package com.learn.community.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ElasticUtil {

    private ElasticUtil(){}

    public static Class<?> getClazz(String clazzName){
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @date 2019/10/26 0:01
     * @param queryBuilder  设置查询对象
     * @param from  设置from选项，确定要开始搜索的结果索引。 默认为0。
     * @param size  设置大小选项，确定要返回的搜索匹配数。 默认为10。
     * @param timeout
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @throws
     * @since
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder, int from, int size, int timeout){



        //使用默认选项创建 SearchSourceBuilder 。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置查询对象。可以使任何类型的 QueryBuilder
        if (null != queryBuilder) {
            sourceBuilder.query(queryBuilder);
        }
        HighlightBuilder highlightBuilder = new HighlightBuilder(); //创建一个新的HighlightBuilder。
        //HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("articleName");  //为title字段创建字段高光色。
//        highlightTitle.highlighterType("unified"); // 设置字段高光色类型。
//        highlightBuilder.field(highlightTitle);   //将字段高光色添加到高亮构建器。
//        highlightBuilder.fields().add(new HighlightBuilder.Field("articleName"));
//        highlightBuilder.fields().add(new HighlightBuilder.Field("folderName"));
//        highlightBuilder.fields().add(new HighlightBuilder.Field("markContent"));
//        highlightBuilder.fields().add(new HighlightBuilder.Field("htmlContent"));
//
        highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span class=\"highlight\">");
        highlightBuilder.postTags("</span>");
        highlightBuilder.fragmentSize(800000).numOfFragments(0);
        highlightBuilder.fields().add(new HighlightBuilder.Field("articleName"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("folderName"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("markContent"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("htmlContent"));
        sourceBuilder.highlighter(highlightBuilder);
        //设置from选项，确定要开始搜索的结果索引。 默认为0。
        sourceBuilder.from(from);
        //设置大小选项，确定要返回的搜索匹配数。 默认为10。
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeout, TimeUnit.SECONDS));
        return sourceBuilder;
    }

    /**
     * @date 2019/10/26 0:01
     * @param queryBuilder
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @throws
     * @since
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder){
        return initSearchSourceBuilder(queryBuilder,0,10,60);
    }
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder, int from, int size){
        return initSearchSourceBuilder(queryBuilder,from,size,60);
    }
}
