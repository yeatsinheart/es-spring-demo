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
* 原始模板/misc/upAmountBalance
**/
public class UpAmountBalance implements BaseDao {
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
          "event" : [1017]
        }
      }
    }
  },
  "aggs": {
    "1": {
      "date_histogram": {
        "field": "@timestamp",
        "interval": "1M",
        "time_zone": "Asia/Shanghai",
        "min_doc_count": 1
      },
      "aggs": {
        "creditAmount": {
          "sum": {
            "field": "1017.creditAmount"
          }
        },
        "settleAmount": {
          "sum": {
            "field": "1017.settleAmount"
          }
        },
        "restAmount": {
          "sum": {
            "field": "1017.restAmount"
          }
        },
        "deductionAmount": {
          "sum": {
            "field": "1017.deductionAmount"
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
