package com.example.esspringdemo.service.game.dao.detail;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/detail/rechargeWithdraw
**/
public class RechargeWithdraw implements BaseDao {
    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        BoolQueryBuilder select = QueryBuilders.boolQuery();
//todo must
        select.filter(QueryBuilders.termsQuery("event", Arrays.asList(1000, 1001)));
        select.should(QueryBuilders.termsQuery("1000.status", Arrays.asList(1)));
        select.should(QueryBuilders.termsQuery("1001.status", Arrays.asList(1)));
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1);
        TermsBuilder uid = AggregationBuilders.terms("uid").size(1).field("uid");
        //todo size order
        TermsBuilder userName = AggregationBuilders.terms("userName").size(1).field("userName");
        TermsBuilder parentName = AggregationBuilders.terms("parentName").size(1).field("parentName");

        SumBuilder rechargeAmount = AggregationBuilders.sum("rechargeAmount").field("1000.amount");
        SumBuilder withdrawAmount = AggregationBuilders.sum("withdrawAmount").field("1001.amount");
        ValueCountBuilder rechargeCnt =  AggregationBuilders.count("rechargeCnt").field("1000.status");
        ValueCountBuilder withdrawCnt =  AggregationBuilders.count("withdrawCnt").field("1001.status");
        uid.subAggregation(userName).subAggregation(parentName).subAggregation(rechargeAmount).subAggregation(withdrawAmount).subAggregation(rechargeAmount).subAggregation(rechargeCnt).subAggregation(withdrawCnt);


        searchSourceBuilder.query(select).size(0).aggregation(uid);
        return searchSourceBuilder;
    }

    /**
     * 查询响应封装
     *
     * @param args
     * @param response
     */
    @Override
    public DaoResult get(DaoArgs args, SearchResponse response) {
        DaoResult result = new DaoResult<>();
        List list = new ArrayList<>();


        result.setResult(list);
        return result;
    }
}
