package com.example.esspringdemo.service.game.dao.detail;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/detail/bettingRechargeCnt
**/
public class BettingRechargeCnt implements BaseDao {
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
        select.should(QueryBuilders.termsQuery("1000.status", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("1001.status", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("event", Arrays.asList(1008)))
                .should(QueryBuilders.termsQuery("event", Arrays.asList(1010)));
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1).filter(QueryBuilders.termsQuery("event", Arrays.asList(1000, 1001, 1003,1008,1010)));
        CardinalityBuilder uid = AggregationBuilders.cardinality("uid").precisionThreshold(1000);
        /*
      "must": [%s],
        */
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
