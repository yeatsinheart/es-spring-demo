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
* 原始模板/proxy/game
**/
public class Game implements BaseDao {
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
  "aggs": {
    "game": {
      "terms": {
        "field": "1003.gamePlatformId",
        "size": 1000
      },
      "aggs": {
        "betWays": {
          "terms": {
            "field": "1003.betWays",
            "size": 10
          },
          "aggs": {
            "winSum": {
              "sum": {
                "field": "1003.win"
              }
            },
            "effectSum": {
              "sum": {
                "field": "1003.effectiveAmount"
              }
            },
            "playerBetSum": {
              "sum": {
                "field": "1003.playerAmount"
              }
            },
            "playerWaterFee": {
              "sum": {
                "field": "1003.waterFee"
              }
            }
          }
        }
      }
    }
  },
  "size": 0,
  "query": {
    "bool": {
      "must": [%s],
      "filter" : {
        "terms" : {
          "1003.status" : [1]
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