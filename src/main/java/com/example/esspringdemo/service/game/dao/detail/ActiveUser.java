package com.example.esspringdemo.service.game.dao.detail;

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
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/detail/activeUser
**/
public class ActiveUser implements BaseDao {
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

        // todo select.must();

        select.should(QueryBuilders.termsQuery("1000.status", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("1001.status", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("event", Arrays.asList(1009)));
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1).filter(QueryBuilders.termsQuery("event", Arrays.asList(1000, 1001, 1003, 1009)));

        TermsBuilder uid = AggregationBuilders.terms("uid").field("uid");
        // todo uid.size();
        // todo uid.order(Terms.Order.aggregation());
        /*子查询*/
        TermsBuilder userName = AggregationBuilders.terms("userName").size(1).field("userName");
        TermsBuilder regTime = AggregationBuilders.terms("regTime").size(1).field("regTime");
        TermsBuilder parentName = AggregationBuilders.terms("parentName").size(1).field("parentName");
        SumBuilder rechargeAmount = AggregationBuilders.sum("rechargeAmount").field("1000.amount");
        SumBuilder withdrawAmount = AggregationBuilders.sum("withdrawAmount").field("1001.amount");
        SumBuilder bettingAmount = AggregationBuilders.sum("bettingAmount").field("1003.amount");
        SumBuilder bonusAmount = AggregationBuilders.sum("bonusAmount").field("1009.amount");

        uid.subAggregation(userName)
                .subAggregation(regTime)
                .subAggregation(parentName)
                .subAggregation(rechargeAmount)
                .subAggregation(withdrawAmount)
                .subAggregation(bettingAmount)
                .subAggregation(bonusAmount);
        /*
        {
  "size": 0,
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
      "aggregations": {

      }
    }
  }
}

        */
        searchSourceBuilder.query(select).aggregation(uid).size(0);
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
