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
* 原始模板/detail/balance
**/
public class Balance implements BaseDao {
    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("event", Arrays.asList(1016)));;
        // todo select.must();
        TermsBuilder uid = AggregationBuilders.terms("uid").field("uid");
        // todo uid.size();
        // todo uid.order(Terms.Order.aggregation());
        /*子查询*/
        TermsBuilder userName = AggregationBuilders.terms("userName").size(1).field("userName");
        TermsBuilder userType = AggregationBuilders.terms("userType").size(1).field("userType");
        SumBuilder rechargeAmount = AggregationBuilders.sum("rechargeAmount").field("1000.amount");
        SumBuilder withdrawAmount = AggregationBuilders.sum("withdrawAmount").field("1001.amount");

        uid.subAggregation(userName)
                .subAggregation(userType)
                .subAggregation(rechargeAmount)
                .subAggregation(withdrawAmount);
        /*
  "query": {
    "bool": {
      "must": [%s],

    }
  },
  "aggregations": {
    "uid": {
      "terms": {
        "size": %d,
        "order": {
          "%s": "%s"
        }
      },
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
