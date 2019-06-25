package com.example.esspringdemo.service.game;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public class BaseQuery {
    public static void baseBoolQuery(BoolQueryBuilder query, Long startTime, Long endTime, String project, Byte isFake) {
        //null == endTime ?:
        LocalDateTime now = LocalDateTime.now();
        query.must(QueryBuilders.rangeQuery("@timestamp")
                .gte(null == startTime ? now.with(LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli() : startTime)
                .lte(null == endTime ? System.currentTimeMillis() + 999 : endTime).format("epoch_millis"));
        query.must(QueryBuilders.termsQuery("project", null == project ? "betball" : project));
        if (null != isFake && 2 != isFake) {
            query.must(QueryBuilders.termsQuery("isFake", Arrays.asList(isFake)));
        }
    }
}
