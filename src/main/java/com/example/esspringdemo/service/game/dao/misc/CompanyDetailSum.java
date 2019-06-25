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
* 原始模板/misc/companyDetailSum
**/
public class CompanyDetailSum implements BaseDao {
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
      "filter" : [{
        "terms" : {
          "event" : [1000, 1001, 1003, 1009, 1010,1011,1015,1018]
        }
      }],
      "should" : [
        { "term" : { "1000.status" : 1 } },
        { "term" : { "1001.status" : 1 } },
        { "term" : { "1003.status" : 1 } },
        { "term" : { "1011.status" : 1 } },
        { "term" : { "1015.status" : 1 } },
        { "term" : { "1018.status" : 1 } },
        { "term" : { "event" : 1009} },
        { "term" : { "event" : 1010} }
      ],
      "minimum_should_match" : 1,
      "adjust_pure_negative": true,
      "boost": 1
    }
  },
  "aggs": {
        "rechargeSum": {
          "sum": {
            "field": "1000.amount"
          }
        },
        "rechargeNum": {
          "cardinality": {
            "field": "1000.userId",
            "precision_threshold": 1000
          }
        },
        "withdrawNum": {
          "cardinality": {
            "field": "1001.userId",
            "precision_threshold": 1000
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
        "betWays": {
            "terms": {
              "field": "1003.betWays",
              "size": 10
            },
            "aggs": {
                "betEffectSum": {
                  "sum": {
                    "field": "1003.effectiveAmount"
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
                "winSum": {
                  "sum": {
                    "field": "1003.win"
                  }
                }
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
        },
        "playerWinSum": {
          "sum": {
            "field": "1003.playerWin"
          }
        },
        "totalWin": {
          "sum": {
            "field": "1003.totalWin"
          }
        },
        "bonusSum":{
          "sum": {
            "field": "1009.amount"
          }
        },
        "addCoinSum":{
          "sum": {
            "field": "1018.amount"
          }
        },
        "subCoinSum":{
          "sum": {
            "field": "1011.amount"
          }
        },
        "upAmountSum":{
          "sum": {
            "field": "1015.amount"
          }
        },
        "upAmountTimes":{
          "value_count": {
            "field": "1015.status"
          }
        },
        "bettingTimes":{
          "value_count": {
            "field": "1003.orderId"
          }
        },
        "rebateSum":{
          "sum": {
            "field": "1010.amount"
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
