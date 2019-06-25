package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import com.example.esspringdemo.service.game.model.commission.BonusArgs;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rebate  implements BaseDao {
    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();

        SumBuilder amountSum = AggregationBuilders.sum("amountSum").field("1010.amount");
        BoolQueryBuilder select = QueryBuilders.boolQuery();
        select.must(QueryBuilders.termsQuery("event",Arrays.asList(1010)));
        baseBoolQuery(select,args);
        if (null != ((BettingArgs) args).getProxyId()) {
            select.must(QueryBuilders.termsQuery("parentId", ((BettingArgs) args).getProxyId()));
        }
        searchSourceBuilder.query(select).aggregation(amountSum).size(0);
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
        DaoResult<BigDecimal> result = new DaoResult<>();
        Sum amountSum=response.getAggregations().get("amountSum");

        result.setResult(getMoney(new BigDecimal(amountSum.getValue())));
        return result;
    }
}
