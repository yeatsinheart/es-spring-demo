package com.example.esspringdemo.service.game.dao.device;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/device/uvByDay
**/
public class UvByDay implements BaseDao {
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
        //todo select.must();
        select.must(QueryBuilders.termsQuery("event", Arrays.asList(100012)));
        DateHistogramBuilder group = AggregationBuilders.dateHistogram("group").field("timestamp").interval(DateHistogramInterval.DAY).timeZone("Asia/Shanghai").minDocCount(1);
        TermsBuilder field = AggregationBuilders.terms("field").size(5).order(Terms.Order.aggregation("_count",false));
        //todo field.field("");
        CardinalityBuilder uuid = AggregationBuilders.cardinality("uuid").precisionThreshold(1000);
        field.subAggregation(uuid);
        group.subAggregation(field);
        searchSourceBuilder.query(select).size(0).aggregation(group);
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
