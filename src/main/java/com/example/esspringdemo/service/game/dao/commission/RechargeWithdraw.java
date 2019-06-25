package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.commission.ProxyUserType;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import com.example.esspringdemo.service.game.model.commission.RechargeWithdrawReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.math.BigDecimal;
import java.util.Arrays;

public class RechargeWithdraw implements BaseDao {
    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();

        SumBuilder rechargeAmount = AggregationBuilders.sum("rechargeAmount").field("1000.amount");
        SumBuilder withdrawAmount = AggregationBuilders.sum("withdrawAmount").field("1001.amount");
        SumBuilder rechargeCommFee = AggregationBuilders.sum("rechargeCommFee").field("1000.commFee");
        SumBuilder withdrawCommFee = AggregationBuilders.sum("withdrawCommFee").field("1001.commFee");

        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("event", Arrays.asList(1000, 1001)));
        select.should(QueryBuilders.termsQuery("1000.status", Arrays.asList(1)));
        select.should(QueryBuilders.termsQuery("1001.status", Arrays.asList(1)));
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1);
        baseBoolQuery(select, args);
        if (null != ((BettingArgs) args).getProxyId()) {
            select.must(QueryBuilders.termsQuery("parentId", ((BettingArgs) args).getProxyId()));
        }
        select.must(QueryBuilders.termsQuery("userType", Arrays.asList(ProxyUserType.MEMBER.value())));
        searchSourceBuilder.size(0).query(select).aggregation(rechargeAmount).aggregation(withdrawAmount).aggregation(rechargeCommFee).aggregation(withdrawCommFee);
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
        DaoResult<RechargeWithdrawReport> result = new DaoResult<>();

        Sum rechargeAmount = response.getAggregations().get("rechargeAmount");
        Sum withdrawAmount = response.getAggregations().get("withdrawAmount");
        Sum rechargeCommFee = response.getAggregations().get("rechargeCommFee");
        Sum withdrawCommFee = response.getAggregations().get("withdrawCommFee");

        RechargeWithdrawReport report = RechargeWithdrawReport.builder()
                .rechargeAmount(getMoney(new BigDecimal(rechargeAmount.getValue())))
                .withdrawAmount(getMoney(new BigDecimal(withdrawAmount.getValue())))
                .rechargeCommFee(getMoney(new BigDecimal(rechargeCommFee.getValue())))
                .withdrawCommFee(getMoney(new BigDecimal(withdrawCommFee.getValue())))
                .build();
        result.setResult(report);
        return result;
    }
}
