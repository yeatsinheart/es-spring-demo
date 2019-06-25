package com.example.esspringdemo.service.game.dao.betting;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.ListArgs;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public class ListSum implements BaseDao {


    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource();
        SumBuilder effectiveAmount = AggregationBuilders.sum("effectiveAmount").field("1003.effectiveAmount");
        SumBuilder validAmount = AggregationBuilders.sum("validAmount").field("1003.validAmount");
        SumBuilder amount = AggregationBuilders.sum("amount").field("1003.amount");
        SumBuilder prize = AggregationBuilders.sum("prize").field("1003.prize");
        SumBuilder win = AggregationBuilders.sum("win").field("1003.win");
        SumBuilder totalWin = AggregationBuilders.sum("totalWin").field("1003.totalWin");

        BoolQueryBuilder select = QueryBuilders.boolQuery().adjustPureNegative(true).boost(1);
        LocalDateTime now = LocalDateTime.now();
        args.setStartTime(null == args.getStartTime() ? now.with(LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli() : args.getStartTime());
        args.setEndTime(null == args.getEndTime() ? now.with(LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli() : args.getEndTime());

        buildFakeWhere(select, args);
        select.must(QueryBuilders.termsQuery("event", Arrays.asList(EventEnum.BETTING.value())));
        select.must(QueryBuilders.rangeQuery("1003.createTime").gte(args.getStartTime()));
        select.must(QueryBuilders.rangeQuery("1003.createTime").gte(args.getEndTime()));
        if (null != ((ListArgs) args).getPayoutStartTime()) {
            select.must(QueryBuilders.rangeQuery("1003.payoutTime").gte(((ListArgs) args).getPayoutStartTime()));
        }
        if (null != ((ListArgs) args).getPayoutEndTime()) {
            select.must(QueryBuilders.rangeQuery("1003.payoutTime").lte(((ListArgs) args).getPayoutEndTime()));
        }
        if (null != ((ListArgs) args).getCreateStartTime()) {
            select.must(QueryBuilders.rangeQuery("1003.syncTime").gte(((ListArgs) args).getCreateStartTime()));
        }
        if (null != ((ListArgs) args).getCreateEndTime()) {
            select.must(QueryBuilders.rangeQuery("1003.syncTime").lte(((ListArgs) args).getCreateEndTime()));
        }
        if (null != ((ListArgs) args).getMinAmount()) {
            select.must(QueryBuilders.rangeQuery("1003.amount").gte(((ListArgs) args).getMinAmount()));
        }
        if (null != ((ListArgs) args).getMaxAmount()) {
            select.must(QueryBuilders.rangeQuery("1003.amount").lte(((ListArgs) args).getMaxAmount()));
        }
        if (null != ((ListArgs) args).getMinProfit()) {
            select.must(QueryBuilders.rangeQuery("1003.win").gte(((ListArgs) args).getMinProfit()));
        }
        if (null != ((ListArgs) args).getMaxProfit()) {
            select.must(QueryBuilders.rangeQuery("1003.win").lte(((ListArgs) args).getMaxProfit()));
        }

        if (null != ((ListArgs) args).getUsername()) {
            select.must(QueryBuilders.termsQuery("userName", ((ListArgs) args).getMaxProfit()));
        }
        if (null != ((ListArgs) args).getProxy()) {
            select.must(QueryBuilders.termsQuery("parentName", ((ListArgs) args).getProxy()));
        }
        if (null != ((ListArgs) args).getOrderId()) {
            select.must(QueryBuilders.termsQuery("1003.orderId", ((ListArgs) args).getOrderId()));
        }
        if (null != ((ListArgs) args).getMerOrderId()) {
            select.must(QueryBuilders.termsQuery("1003.merOrderId", ((ListArgs) args).getOrderId()));
        }
        if (null != ((ListArgs) args).getBetWays()) {
            select.must(QueryBuilders.termsQuery("1003.betWays", Arrays.asList(((ListArgs) args).getBetWays())));
        }
        if (null != ((ListArgs) args).getStatus() && (byte) -1 != ((ListArgs) args).getStatus()) {
            select.must(QueryBuilders.termsQuery("1003.status", Arrays.asList(((ListArgs) args).getStatus())));
            if (null != ((ListArgs) args).getGamePlatformId() && ((ListArgs) args).getGamePlatformId().size() == 1 && 8000L == ((ListArgs) args).getGamePlatformId().get(0)) {
                select.must(QueryBuilders.termsQuery("1003.ticketStatus", Arrays.asList(((ListArgs) args).getStatus())));
            }
        }
        if (null != ((ListArgs) args).getGamePlatformId()) {
            ((ListArgs) args).getGamePlatformId().forEach(platformId -> {
                select.should(QueryBuilders.termsQuery("1003.betWays", Arrays.asList(platformId)));
            });
        } else {
            ((ListArgs) args).setShouldMatchNum(1);
        }
        select.minimumNumberShouldMatch(((ListArgs) args).getShouldMatchNum());

        sourceBuilder.size(0).query(select).aggregation(effectiveAmount).aggregation(validAmount).aggregation(amount).aggregation(prize).aggregation(win).aggregation(totalWin);
        return sourceBuilder;
    }

    /**
     * 查询响应封装
     *
     * @param args
     * @param response
     */
    @Override
    public DaoResult get(DaoArgs args, SearchResponse response) {
        return null;
    }
}
