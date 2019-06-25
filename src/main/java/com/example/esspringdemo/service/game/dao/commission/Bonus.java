package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.commission.BonusArgs;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.math.BigDecimal;
import java.util.Arrays;

public class Bonus implements BaseDao {

    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        TermsQueryBuilder event = QueryBuilders.termsQuery("event", Arrays.asList(EventEnum.COMMISSION.value()));

        SumBuilder amount = AggregationBuilders.sum("amount").field("1003.amount");
        BoolQueryBuilder select = QueryBuilders.boolQuery().must(event);
        baseBoolQuery(select, args);
        select.must(event);
        if (null != ((BonusArgs) args).getProxyId()) {
            select.must(QueryBuilders.termsQuery("parentId", ((BonusArgs) args).getProxyId()));
        }
        searchSourceBuilder.size(0).query(select).aggregation(amount);
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
        Sum amount = response.getAggregations().get("amount");

        result.setResult(getMoney(new BigDecimal(amount.getValue())));
        return result;
    }
}
