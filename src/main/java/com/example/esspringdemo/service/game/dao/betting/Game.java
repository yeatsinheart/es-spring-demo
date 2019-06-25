package com.example.esspringdemo.service.game.dao.betting;

import com.example.esspringdemo.service.game.GameConfig;
import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.GameReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * /template/betting/game.tpl
 */
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
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)))
                .must(QueryBuilders.termsQuery("event", Arrays.asList(EventEnum.BETTING.value())));
        baseBoolQuery(boolQueryBuilder, args);
        String proxyName = null;
        if (null != proxyName) {
            boolQueryBuilder.must(QueryBuilders.termsQuery("parentName", proxyName));
        }
        //需要执行的语句
        TermsBuilder game = AggregationBuilders.terms("game").field("1003.gamePlatformId").order(Terms.Order.aggregation("totalWinSum", false)).size(1000);
        SumBuilder totalWinSum = AggregationBuilders.sum("totalWinSum").field("1003.totalWin");
        SumBuilder amountSum = AggregationBuilders.sum("amountSum").field("1003.amount");
        //整合语句的结构
        game.subAggregation(totalWinSum).subAggregation(amountSum);
        searchSourceBuilder.aggregation(game).query(boolQueryBuilder).size(0);
        return searchSourceBuilder;
    }

    /**
     * 查询响应封装
     *
     * @param args
     * @param response
     */
    @Override
    public DaoResult<List<GameReport>> get(DaoArgs args, SearchResponse response) {
        Terms games = response.getAggregations().get("game");
        List<GameReport> reportList = new ArrayList();
        for (Terms.Bucket game : games.getBuckets()) {
            Long platforomID = Long.parseLong(game.getKey().toString());
            Sum totalWinSum = game.getAggregations().get("totalWinSum");
            Sum amountSum = game.getAggregations().get("amountSum");
            reportList.add(new GameReport(game.getKeyAsString(), platforomID, getMoney(new BigDecimal(totalWinSum.getValue())), getMoney(new BigDecimal(amountSum.getValue()))));
        }
        DaoResult result = new DaoResult();
        result.setResult(reportList);
        return result;
    }
}
