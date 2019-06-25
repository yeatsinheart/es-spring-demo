package com.example.esspringdemo.service.game.dao.detail;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;
/**
* 原始模板/detail/firstRechargeAllSum
**/
public class FirstRechargeAllSum implements BaseDao {
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
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1);
        // todo select.must();
        // todo select..filter(QueryBuilders.termsQuery("event", Arrays.asList()));
        //todo  select.should(QueryBuilders.termsQuery("1000.first", Arrays.asList(1)))
        //                .should(QueryBuilders.termsQuery("1015.first", Arrays.asList(1)));
        SumBuilder rechargeAmount = AggregationBuilders.sum("rechargeAmount").field("1000.amount");
        SumBuilder upAmount = AggregationBuilders.sum("upAmount").field("1015.amount");

        searchSourceBuilder.query(select).aggregation(rechargeAmount).aggregation(upAmount);
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
