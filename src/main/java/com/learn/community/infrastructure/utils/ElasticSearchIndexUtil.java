package com.learn.community.infrastructure.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ElasticSearchIndexUtil {
	// 引入 Ela 连接实列化对象
	@Autowired
	private TransportClient client;
 
/**
	 * 功能描述:多条件检索之constant score query
	 * @param indexs 索引数组
	 * @param types  类型数组
	 * @param name   文档属性名称
	 * @param value  文档属性值
	 * @param boost  权重值
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public SearchResponse searchConstantScoreQuery(String[] indexs, String[] types, String name, Object value, float boost) throws InterruptedException, ExecutionException {
		TermQueryBuilder termQuery = QueryBuilders.termQuery(name, value);
		
		ConstantScoreQueryBuilder constantScoreQuery = QueryBuilders.constantScoreQuery(termQuery).boost(boost);
		SearchResponse response = client.prepareSearch(indexs).setTypes(types).setQuery(constantScoreQuery).execute().get();
		return response;
	}
	
	
	/**
	 * 功能描述:多条件查询之bool query
	 * @param indexs 索引数组
	 * @param types  类型数组
	 * @param musts  必须查询
	 * @param mustNots 不能查询
	 * @param shoulds  应查询
	 * @param filters  拦截条件
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public SearchResponse searchBoolQuery(String[] indexs, String[] types, List<QueryBuilder> musts, List<QueryBuilder> mustNots, List<QueryBuilder> shoulds, List<QueryBuilder> filters) throws InterruptedException, ExecutionException{
		BoolQueryBuilder boolQuery =  QueryBuilders.boolQuery();
		
		musts.stream().forEach(item->{
			boolQuery.must(item);
		});
		mustNots.stream().forEach(item->{
			boolQuery.mustNot(item);
		});
		shoulds.stream().forEach(item ->{
			boolQuery.should(item);
		});
		filters.stream().forEach(item->{
			boolQuery.filter(item);
		});
		
		SearchResponse response = client.prepareSearch(indexs).setTypes(types).setQuery(boolQuery).execute().get();
		return response;
	}
	
	/**
	 * 功能描述:多条件查询之dis max query
	 * @param indexs   索引数组
	 * @param types    类型数组
	 * @param query    查询条件对列
	 * @param boost    权重值
	 * @param breaker  判断值
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public SearchResponse searchDisMaxQuery(String[] indexs, String[] types, List<QueryBuilder> query, float boost, float breaker) throws InterruptedException, ExecutionException{
		DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
		query.stream().forEach(item ->{
			disMaxQuery.add(item);
		});
		disMaxQuery.boost(boost).tieBreaker(breaker);
		SearchResponse response = client.prepareSearch(indexs).setTypes(types).setQuery(disMaxQuery).execute().get();
		return response;
	}
	
	/**
	 *  功能描述:多条件查询之function score query
	 * @param indexs 索引数组
	 * @param types  类型数组
	 * @param matchQuery  条件
	 * @param fieldName   文档属性名称
	 * @param origin  
	 * @param scale
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public SearchResponse searchFunctionScoreQuery(String[] indexs, String[] types, QueryBuilder matchQuery, String fieldName, Object origin, Object scale) throws InterruptedException, ExecutionException{
		FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
				new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery,  ScoreFunctionBuilders.randomFunction().seed(Math.round(Math.random() * 100))),
				new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders.exponentialDecayFunction(fieldName, origin, scale))
		};
		SearchResponse response = client.prepareSearch(indexs).setTypes(types).setQuery(QueryBuilders.functionScoreQuery(functions)).execute().get();
		return response;
	}
	
	/**
	 * 功能描述:多条件查询之boost query
	 * @param indexs 索引数组
	 * @param types  类型数组
	 * @param positiveQuery   条件
	 * @param negativeQuery   条件
	 * @param boost  权重值
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public SearchResponse searchBoostiQuery(String[] indexs, String[] types, QueryBuilder positiveQuery,  QueryBuilder negativeQuery, float boost) throws InterruptedException, ExecutionException{
		BoostingQueryBuilder boostingQuery = QueryBuilders.boostingQuery(positiveQuery, negativeQuery);
		boostingQuery.boost(boost);
		
		SearchResponse response = client.prepareSearch(indexs).setTypes(types).setQuery(boostingQuery).execute().get();
		return response;
	}
}