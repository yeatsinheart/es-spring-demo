package com.example.esspringdemo.service.game.dao.risk;

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
* 原始模板/risk/ipLogin
**/
public class IpLogin implements BaseDao {
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
  "query": {
    "bool": {
      "must":[{"term":{"event":1005}}],
      "filter" : {
        "terms" : {
          "uid":[%s]
        }
      }
    }
  },
  "aggs": {
    "uid": {
      "terms": {
        "field": "uid",
        "size": %d
      },
      "aggs": {
        "top": {
          "top_hits": {
            "sort": [{
              "@timestamp": {
                "order": "desc"
              }
            }],
            "_source": {
              "includes": [
                "ip",
                "@timestamp",
                "geoip.country_name",
                "geoip.region_name",
                "geoip.city_name"
              ]
            },
            "size": 1
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
