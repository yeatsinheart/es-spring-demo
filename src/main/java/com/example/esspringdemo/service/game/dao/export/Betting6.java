package com.example.esspringdemo.service.game.dao.export;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/export/betting6
**/
public class Betting6 implements BaseDao {
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
//todo        select.must()
        select.filter(QueryBuilders.termsQuery("event", Arrays.asList(1000,1001,1003,1009,1010,1015)));
        select.should(QueryBuilders.termsQuery("1000.status",Arrays.asList(1)));
        select.should(QueryBuilders.termsQuery("1001.status",Arrays.asList(1)));
        select.should(QueryBuilders.termsQuery("1003.status",Arrays.asList(1)));
        select.should(QueryBuilders.termsQuery("1015.status",Arrays.asList(1)));
        select.should(QueryBuilders.termsQuery("event",Arrays.asList(1009)));
        select.should(QueryBuilders.termsQuery("event",Arrays.asList(1010)));
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1);

        TermsBuilder uid = AggregationBuilders.terms("uid").order(Terms.Order.aggregation("_term",false));
        //todo uid.size(5)
        TopHitsBuilder firstRecharge  = AggregationBuilders.topHits("firstRecharge").addSort(SortBuilders.fieldSort("1000.createTime").order(SortOrder.ASC))
                .setFetchSource(new String[]{ "1000.amount"},new String[]{ }).setSize(1);
        TermsBuilder userName = AggregationBuilders.terms("userName").size(1).field("userName");
        TermsBuilder parentName = AggregationBuilders.terms("parentName").size(1).field("parentName");
        TermsBuilder regTime = AggregationBuilders.terms("regTime").size(1).field("regTime");
        SumBuilder recharge = AggregationBuilders.sum("recharge").field("1000.amount");
        SumBuilder upAmount = AggregationBuilders.sum("upAmount").field("1015.amount");

        SumBuilder withdraw = AggregationBuilders.sum("withdraw").field("1001.amount");
        SumBuilder betting = AggregationBuilders.sum("betting").field("1003.effectiveAmount");
        SumBuilder prize = AggregationBuilders.sum("prize").field("1003.prize");
        SumBuilder betWin = AggregationBuilders.sum("betWin").field("1003.win");
        SumBuilder rebate = AggregationBuilders.sum("rebate").field("1010.amount");
        SumBuilder bonus = AggregationBuilders.sum("bonus").field("1009.amount");
        uid.subAggregation(firstRecharge).subAggregation(userName)
        .subAggregation(parentName)
        .subAggregation(regTime)
        .subAggregation(recharge)
        .subAggregation(upAmount)
        .subAggregation(withdraw)
        .subAggregation(betting)
        .subAggregation(prize)
        .subAggregation(betWin)
        .subAggregation(rebate)
        .subAggregation(bonus);


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
