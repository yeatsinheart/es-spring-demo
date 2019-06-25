package com.example.esspringdemo.service.game.dao.betting;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.DateHistory;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.GameByTimeReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.joda.time.DateTimeZone;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameByTime implements BaseDao {
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
        DateHistogramBuilder group = AggregationBuilders.dateHistogram("group")
                .field("@timestamp").interval(DateHistogramInterval.HOUR)
                .timeZone(DateTimeZone.forID("Asia/Shanghai").getID()).format("H时").minDocCount(1L);
        if(args.getEndTime()-args.getStartTime()< DateHistory.ONE_DAY){
            group.interval(DateHistogramInterval.HOUR);
        }else{
            group.interval(DateHistogramInterval.DAY);
        }
        TermsBuilder platformTerms = AggregationBuilders.terms("game").field("1003.gamePlatformId").order(Terms.Order.aggregation("totalWinSum", false)).size(1000);
        SumBuilder totalWinSum = AggregationBuilders.sum("totalWinSum").field("1003.totalWin");
        SumBuilder amountSum = AggregationBuilders.sum("amountSum").field("1003.amount");
        //整合语句的结构
        platformTerms.subAggregation(totalWinSum).subAggregation(amountSum);
        group.subAggregation(platformTerms);
        searchSourceBuilder.aggregation(group).query(boolQueryBuilder).size(0);
        return searchSourceBuilder;
    }

    /**
     * 查询响应封装
     *
     * @param args
     * @param response
     */
    @Override
    public DaoResult<List<GameByTimeReport>> get(DaoArgs args, SearchResponse response) {
        Histogram groups = response.getAggregations().get("group");
        List<GameByTimeReport> reportList = new ArrayList();
        for (Histogram.Bucket group : groups.getBuckets()) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("");
            Terms games = group.getAggregations().get("game");
            for (Terms.Bucket platform : games.getBuckets()) {
                Long platforomID = Long.parseLong(platform.getKey().toString());
                Sum sumTotal = platform.getAggregations().get("totalWinSum");
                Sum sumAmount = platform.getAggregations().get("amountSum");
                reportList.add(new GameByTimeReport(group.getKeyAsString(), platforomID, null, getMoney(new BigDecimal(sumTotal.getValue())), getMoney(new BigDecimal(sumTotal.getValue()))));
            }
        }
        DaoResult result = new DaoResult();
        result.setResult(reportList);
        return result;
    }
}
