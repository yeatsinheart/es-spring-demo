package com.example.esspringdemo.service.game.dao.retention;

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
* 原始模板/retention/bettingGame
**/
public class BettingGame implements BaseDao {
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
  "size":0,
  "aggs":{
    "2":{
      "date_histogram":{
        "field":"regTime",
        "interval":"%s",
        "offset":"%s",
        "time_zone":"Asia/Shanghai",
        "min_doc_count":1
      },
      "aggs":{
        "3":{
          "terms":{
            "field":"%s",
            "size":31,
            "order":{
              "_term":"asc"
            }
          },
          "aggs":{
            "1":{
              "cardinality":{
                "field":"uid",
                "precision_threshold": 1000
              }
            }
          }
        }
      }
    }
  },
  "query":{
    "bool":{
      "must":[%s],
      "filter" : [{
        "terms" : {
          "event" : [1003,1004]
        }
      }],
      "should" : [
        { "term" : { "1003.channel" : 10 } },
        { "term" : { "event" : 1004} }
      ],
      "minimum_should_match" : 1,
      "adjust_pure_negative": true,
      "boost": 1
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
