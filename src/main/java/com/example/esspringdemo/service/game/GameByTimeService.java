package com.example.esspringdemo.service.game;

import com.example.esspringdemo.service.ReportService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GameByTimeService extends ElasticSearchService implements ReportService {
    public SearchResponse call() {
        //组合条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1))).must(QueryBuilders.termsQuery("event", Arrays.asList(1003)));
        // BaseQuery.baseQuery(boolQueryBuilder, null, null, null, (byte) 2);
        String proxyName = null;
        if (null != proxyName) {
            boolQueryBuilder.must(QueryBuilders.termsQuery("parentName", proxyName));
        }
        //需要执行的语句
        DateHistogramBuilder gameByTime = AggregationBuilders.dateHistogram("group").field("@timestamp").interval(DateHistogramInterval.HOUR).timeZone(DateTimeZone.forID("Asia/Shanghai").getID()).format("H时").minDocCount(1L);
        TermsBuilder platformTerms = AggregationBuilders.terms("game").field("1003.gamePlatformId").order(Terms.Order.aggregation("totalWinSum", false)).size(1000);
        SumBuilder totalWinSum = AggregationBuilders.sum("totalWinSum").field("1003.totalWin");
        SumBuilder amountSum = AggregationBuilders.sum("amountSum").field("1003.amount");
        //整合语句的结构
        platformTerms.subAggregation(totalWinSum).subAggregation(amountSum);
        gameByTime.subAggregation(platformTerms);
        SearchRequestBuilder requestBuilder = elasticsearchTemplate.getClient().prepareSearch("report-*").setTypes("logs").addAggregation(gameByTime).setQuery(boolQueryBuilder).setSize(0);
        SearchResponse response = requestBuilder.execute().actionGet();
        //get(response);
        return response;

    }

//    public List<GameByTimeReport> init() {
//        return null;
//    }
//
//    public List<GameByTimeReport> get(SearchResponse response) {
//        Histogram groups = response.getAggregations().get("group");
//        List<GameByTimeReport> reportList = new ArrayList();
//        for (Histogram.Bucket group : groups.getBuckets()) {
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("");
//            Terms games = group.getAggregations().get("game");
//            for (Terms.Bucket platform : games.getBuckets()) {
//                Long platforomID = Long.parseLong(platform.getKey().toString());
//                Sum sumTotal = platform.getAggregations().get("totalWinSum");
//                Sum sumAmount = platform.getAggregations().get("amountSum");
//                reportList.add(new GameByTimeReport(group.getKeyAsString(), platforomID, null, new BigDecimal(sumTotal.getValue()).divide(new BigDecimal(1000)), new BigDecimal(sumAmount.getValue()).divide(new BigDecimal(1000))));
//            }
//        }
//        return reportList;
//    }

}
