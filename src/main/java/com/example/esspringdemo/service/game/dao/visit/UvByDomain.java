package com.example.esspringdemo.service.game.dao.visit;

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
* 原始模板/visit/uvByDomain
**/
public class UvByDomain implements BaseDao {
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
    "2": {
      "date_histogram": {
        "field": "timestamp",
        "interval": "1d",
        "time_zone": "Asia/Shanghai",
        "min_doc_count": 1
      },
      "aggs": {
        "1": {
          "cardinality": {
            "field": "uuid",
            "precision_threshold": 1000
          }
        }
      }
    }
  },
  "size": 0,
  "query": {
    "bool": {
      "must": [%s,{"term":{"event":100012}}],
      "filter":[%s]
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
