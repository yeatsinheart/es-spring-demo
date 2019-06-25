package com.example.esspringdemo.service.game.dao.monitor;

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
* 原始模板/monitor/game
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
  "size": 0,
  "query": {
    "bool": {
      "must": [%s,{"term":{"event":1003}}],
      "filter" : {
        "terms" : {
          "1003.status" : [1]
        }
      }
    }
  },
  "aggs": {
    "game": {
      "terms": {
        "field": "1003.gamePlatformId",
        "size": 1000
      },
      "aggs": {
        "betEffectSum": {
          "sum": {
            "field": "1003.effectiveAmount"
          }
        },
        "winSum": {
          "sum": {
            "field": "1003.win"
          }
        },
        "betSum": {
          "sum": {
            "field": "1003.amount"
          }
        },
        "prizeSum": {
          "sum": {
            "field": "1003.prize"
          }
        },
        "betNum": {
          "cardinality": {
            "field": "uid",
            "precision_threshold": 1000
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
