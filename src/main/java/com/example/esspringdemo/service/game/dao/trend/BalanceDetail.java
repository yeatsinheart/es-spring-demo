package com.example.esspringdemo.service.game.dao.trend;

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
* 原始模板/trend/balanceDetail
**/
public class BalanceDetail implements BaseDao {
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
          "event" : [1014]
        }
      }
    }
  },
  "aggregations": {
    "uid": {
      "terms": {
        "size": %d,
        "field": "uid",
        "order":{
          "_term":"asc"
        }
      },
      "aggs": {
        "userName": {
          "terms": {
            "size": 1,
            "field": "userName"
          }
        },
        "userType": {
          "terms": {
            "size": 1,
            "field": "userType"
          }
        },
        "channelId": {
          "terms": {
            "size": 100,
            "field": "1014.channelId"
          },
          "aggs": {
            "sumBalance": {
              "sum": {
                "field":"1014.balance"
              }
            }
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
