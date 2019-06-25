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
* 原始模板/detail/upAmountSum
**/
public class UpAmountSum implements BaseDao {
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
        //todo select.filter(QueryBuilders.termsQuery("event", Arrays.asList()));
        //todo  select.must();
        //todo select.should();

        //todo searchSourceBuilder.from();
        //todo searchSourceBuilder.size();
        SumBuilder upAmountSum = AggregationBuilders.sum("upAmountSum").field("1015.amount");
        SumBuilder upAmountFeeSum = AggregationBuilders.sum("upAmountFeeSum").field("1015.commFee");
        SumBuilder upAmountRebateSum = AggregationBuilders.sum("upAmountRebateSum").field("1015.rebate");
        SumBuilder subAmountSum = AggregationBuilders.sum("subAmountSum").field("1016.amount");
        SumBuilder subAmountFeeSum = AggregationBuilders.sum("subAmountFeeSum").field("1016.commFee");
        SumBuilder subAmountRebateSum = AggregationBuilders.sum("subAmountRebateSum").field("1016.rebate");

        searchSourceBuilder.query(select).size(0).aggregation(upAmountFeeSum).aggregation(upAmountSum).aggregation(upAmountRebateSum).aggregation(subAmountSum).aggregation(subAmountFeeSum).aggregation(subAmountRebateSum);
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
