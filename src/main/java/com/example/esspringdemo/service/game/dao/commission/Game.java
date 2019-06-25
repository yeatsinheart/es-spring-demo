package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.dto.commission.ProxyUserType;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import com.example.esspringdemo.service.game.model.commission.BettingReport;
import com.example.esspringdemo.service.game.model.commission.GameReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

public class Game implements BaseDao {

    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();

        TermsBuilder gamePlateform = AggregationBuilders.terms("gamePlateform").size(1000).field("1003.gamePlatformId");
        SumBuilder totalWin = AggregationBuilders.sum("totalWin").field("1003.totalWin");
        SumBuilder win = AggregationBuilders.sum("win").field("1003.win");
        SumBuilder waterFee = AggregationBuilders.sum("waterFee").field("1003.waterFee");
        gamePlateform.subAggregation(totalWin).subAggregation(win).subAggregation(waterFee);
        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)));
        baseBoolQuery(select,args);

        if(null != ((BettingArgs)args).getProxyId()){
            select.must(QueryBuilders.termsQuery("parentId",((BettingArgs)args).getProxyId()));
        }
        select.must( QueryBuilders.termsQuery("event", Arrays.asList(EventEnum.BETTING.value())));
        select.must(QueryBuilders.termsQuery("userType", Arrays.asList(ProxyUserType.MEMBER.value())));
        searchSourceBuilder.size(0).query(select).aggregation(gamePlateform);
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
        DaoResult<List<GameReport>> result = new DaoResult<>();
        List<GameReport> list = new ArrayList<>();
        Terms gamePlateforms = response.getAggregations().get("gamePlateform");
        for(Terms.Bucket gamePlateform :gamePlateforms.getBuckets()){
            Sum waterFee = gamePlateform.getAggregations().get("waterFee");
            Sum win = gamePlateform.getAggregations().get("win");
            Sum totalWin = gamePlateform.getAggregations().get("totalWin");
           GameReport report = GameReport.builder().platformId(Long.parseLong(gamePlateform.getKey().toString()))
                   .waterFee(getMoney(new BigDecimal(waterFee.getValue())))
                   .win(getMoney(new BigDecimal(win.getValue())))
                   .totalWin(getMoney(new BigDecimal(totalWin.getValue())))
                   .build();
           list.add(report);
        }
        result.setResult(list);
        return result;
    }


}
