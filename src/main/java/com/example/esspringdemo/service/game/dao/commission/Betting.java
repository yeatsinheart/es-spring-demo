package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.dto.commission.ProxyUserType;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import com.example.esspringdemo.service.game.model.commission.BettingReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
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

public class Betting implements BaseDao {

    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        TermsQueryBuilder event = QueryBuilders.termsQuery("event", Arrays.asList(EventEnum.BETTING.value()));
        SumBuilder sumEffectiveAmount = AggregationBuilders.sum("sumEffectiveAmount").field("1003.effectiveAmount");
        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1))).must(event);
        baseBoolQuery(select, args);
        if(null != ((BettingArgs)args).getProxyId()){
            select.must(QueryBuilders.termsQuery("parentId",((BettingArgs)args).getProxyId()));
        }
        select.must(QueryBuilders.termsQuery("userType", Arrays.asList(ProxyUserType.MEMBER.value())));
        TermsBuilder uid = AggregationBuilders.terms("uid").size(10000).field("uid");
        uid.subAggregation(sumEffectiveAmount);
        searchSourceBuilder.query(select).aggregation(uid).size(0);
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
        DaoResult<List<BettingReport>> result = new DaoResult<>();
        List<BettingReport> list = new ArrayList<>();
        Terms uids = response.getAggregations().get("uid");
        for(Terms.Bucket uid :uids.getBuckets()){
            Sum sumEffectiveAmount = uid.getAggregations().get("sumEffectiveAmount");
            BettingReport report = BettingReport.builder().userId(Long.parseLong(uid.getKey().toString())).sumEffectiveAmount(getMoney(new BigDecimal(sumEffectiveAmount.getValue()))).build();
            list.add(report);
        }
        result.setResult(list);
        return result;
    }
}
