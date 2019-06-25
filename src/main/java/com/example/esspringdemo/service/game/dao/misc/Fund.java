package com.example.esspringdemo.service.game.dao.misc;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;
/**
* 原始模板/misc/fund
**/
public class Fund implements BaseDao {
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
        /*
        {
  "size": 0,
  "query": {
    "bool": {
      "must": [%s],
      "filter" : {
        "terms" : {
          "event" : [1000, 1001, 1009, 1010]
        }
      },
      "should" : [
        { "term" : { "1000.status" : 1 } },
        { "term" : { "1001.status" : 1 } },
        { "term" : { "event" : 1009} },
        { "term" : { "event" : 1010} }
      ],
      "minimum_should_match" : 1,
      "adjust_pure_negative": true,
      "boost": 1
    }
  },
  "aggs": {
    "1": {
      "date_histogram": {
        "field": "@timestamp",
        "interval": "1d",
        "time_zone": "Asia/Shanghai",
        "min_doc_count": 1
      },
      "aggs": {
        "rechargeSum": {
          "sum": {
            "field": "1000.amount"
          }
        },
        "withdrawSum": {
          "sum": {
            "field": "1001.amount"
          }
        },
        "rechargeFeeSum": {
          "sum": {
            "field": "1000.fee"
          }
        },
        "withdrawFeeSum": {
          "sum": {
            "field": "1001.fee"
          }
        },
        "bonusSum":{
          "sum": {
            "field": "1009.amount"
          }
        },
        "rebateSum":{
          "sum": {
            "field": "1010.amount"
          }
        }
      }
    }
  }
}

        */
        searchSourceBuilder.query(select);
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
