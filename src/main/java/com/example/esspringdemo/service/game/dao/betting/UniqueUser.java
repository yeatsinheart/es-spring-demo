package com.example.esspringdemo.service.game.dao.betting;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Arrays;
import java.util.List;

public class UniqueUser implements BaseDao {

    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();

        CardinalityBuilder unic = AggregationBuilders.cardinality("unic").field("uid").precisionThreshold(1000);
        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)));
        baseBoolQuery(select, args);
        select.must(QueryBuilders.termsQuery("event",Arrays.asList(EventEnum.BETTING.value())));
        searchSourceBuilder.query(select).aggregation(unic).size(0);
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
        DaoResult<Long> result = new DaoResult<>();
        Cardinality val = response.getAggregations().get("unic");
        result.setResult(val.getValue());
        return result;
    }
}
