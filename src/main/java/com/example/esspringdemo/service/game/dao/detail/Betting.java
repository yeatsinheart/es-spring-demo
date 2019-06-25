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
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/detail/betting
**/
public class Betting implements BaseDao {
    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)));;
        // todo select.must();
        TermsBuilder uid = AggregationBuilders.terms("uid").field("uid");
        // todo uid.size();
        // todo uid.order(Terms.Order.aggregation());
        /*子查询*/
        TermsBuilder userName = AggregationBuilders.terms("userName").size(1).field("userName");
        TermsBuilder parentName = AggregationBuilders.terms("parentName").size(1).field("parentName");

        SumBuilder sumAmount = AggregationBuilders.sum("sumAmount").field("1003.amount");
        SumBuilder sumEffectiveAmount = AggregationBuilders.sum("sumEffectiveAmount").field("1003.effectiveAmount");
        SumBuilder sumPrize = AggregationBuilders.sum("sumPrize").field("1003.prize");
        SumBuilder sumWin = AggregationBuilders.sum("sumWin").field("1003.win");

        uid.subAggregation(userName)
                .subAggregation(parentName)
                .subAggregation(sumAmount)
                .subAggregation(sumEffectiveAmount)
                .subAggregation(sumPrize)
                .subAggregation(sumWin);
        /*
        {
  "query": {
    "bool": {
      "must": [%s,{"term":{"event":1003}}],
    }
  },
  "aggregations": {
    "uid": {
      "terms": {
        "size": %d,
        "field": "uid",
        "order": {
          "%s": "%s"
        }
      },
      "aggregations": {
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
