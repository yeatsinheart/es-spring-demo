package com.example.esspringdemo.service.game.dao.proxy;

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
* 原始模板/proxy/memberDetail
**/
public class MemberDetail implements BaseDao {
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
          "event" : [1000,1001,1003,1009,1010,1015]
        }
      },
      "should" : [
        { "term" : { "1000.status" : 1 } },
        { "term" : { "1001.status" : 1 } },
        { "term" : { "1003.status" : 1 } },
        { "term" : { "1015.status" : 1 } },
        { "term" : { "event":1009 } },
        { "term" : { "event":1010 } }
      ],
      "minimum_should_match" : 1,
      "adjust_pure_negative": true,
      "boost": 1
    }
  },
  "aggregations": {
    "rechargeAmount": {
      "sum": {
        "field": "1000.amount"
      }
    },
    "withdrawAmount": {
      "sum": {
        "field": "1001.amount"
      }
    },
    "bettingAmount": {
      "sum": {
        "field": "1003.amount"
      }
    },
    "betEffectAmount": {
      "sum": {
        "field": "1003.effectiveAmount"
      }
    },
    "betWinAmount": {
      "sum": {
        "field": "1003.totalWin"
      }
    },
    "bonusAmount":{
      "sum": {
        "field": "1009.amount"
      }
    },
    "rebeatAmount":{
      "sum": {
        "field": "1010.amount"
      }
    },
    "upAmount":{
      "sum": {
        "field": "1015.amount"
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
