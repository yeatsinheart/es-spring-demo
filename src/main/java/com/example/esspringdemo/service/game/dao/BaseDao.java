package com.example.esspringdemo.service.game.dao;

import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;

/**
 * 查询处理
 */
public interface BaseDao {
    BigDecimal FACTOR = new BigDecimal(10000);

    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     */
    SearchSourceBuilder initTemple(DaoArgs args);

    /**
     * 查询响应封装
     */
    DaoResult get(DaoArgs args, SearchResponse response);

    /**
     * 通用查询字段
     */
    default void baseBoolQuery(BoolQueryBuilder query, DaoArgs args) {
        LocalDateTime now = LocalDateTime.now();
        args.setStartTime(null == args.getStartTime() ? now.with(LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli() : args.getStartTime());
        //todo 结束shijian
        args.setEndTime(null == args.getEndTime() ? now.with(LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli() : args.getEndTime());
        query.must(QueryBuilders.rangeQuery("@timestamp")
                .gte(args.getStartTime())
                .lte(args.getEndTime()).format("epoch_millis"));
        query.must(QueryBuilders.termsQuery("project", args.getProject()));
        if (2 != args.getIsFake()) {
            query.must(QueryBuilders.termsQuery("isFake", Arrays.asList(args.getIsFake())));
        }
    }

    /**
     * 获取金额：es金额都乘了1万
     */
    default BigDecimal getMoney(BigDecimal raw) {
        return raw.divide(FACTOR);
    }

    default void buildFakeWhere(BoolQueryBuilder query, DaoArgs args) {
        if (args.getIsFake() != 2) {
            query.must(QueryBuilders.termsQuery("isFake", Arrays.asList(args.getIsFake())));
        }
    }
}
