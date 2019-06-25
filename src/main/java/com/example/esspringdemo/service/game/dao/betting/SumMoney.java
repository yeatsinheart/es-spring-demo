package com.example.esspringdemo.service.game.dao.betting;

import com.alibaba.fastjson.JSON;
import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.EventEnum;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SumMoney implements BaseDao {

    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();
        SumBuilder amount = AggregationBuilders.sum("amount").field("1003.amount");
        BoolQueryBuilder select = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("1003.status", Arrays.asList(1)));
        baseBoolQuery(select, args);
        select.must(QueryBuilders.termsQuery("event", Arrays.asList(EventEnum.BETTING.value())));
        searchSourceBuilder.query(select).aggregation(amount);
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
        DaoResult<List<SumMoneyReport>> result = new DaoResult<>();
        List<SumMoneyReport> list = new ArrayList();
        for (SearchHit hit : response.getHits()) {
           Map sourceMap =  hit.sourceAsMap();
            Map<String,Object> hint = (Map<String,Object> )sourceMap.get("1003");
            //todo 就是游戏上报对象
            SumMoneyReport sumMoneyReport = SumMoneyReport.builder()
                    .betWays(Byte.parseByte(hint.get("betWays").toString()))
                    .status(Byte.parseByte(hint.get("status").toString()))

                    .syncTime(Long.parseLong(hint.get("syncTime").toString()))
                    .game(Long.parseLong(hint.get("game").toString()))
                    .channel(Long.parseLong(hint.get("channel").toString()))
                    .payoutTime(Long.parseLong(hint.get("payoutTime").toString()))
                    .createTime(Long.parseLong(hint.get("createTime").toString()))
                    .gamePlatformId(Long.parseLong(hint.get("gamePlatformId").toString()))


                    .orderId(hint.get("orderId").toString())
                    .merUsername(hint.get("merUsername").toString())
                    .gameName(hint.get("gameName").toString())
                    .channelName(hint.get("channelName").toString())
                    .gameCode(hint.get("gameCode").toString())
                    .merOrderId(hint.get("merOrderId").toString())
                    .roundId(hint.get("roundId").toString())
                    .betDetails(hint.get("betDetails").toString())

                    .playerAmount(getMoney(new BigDecimal(hint.get("playerAmount").toString())))
                    .prize(getMoney(new BigDecimal(hint.get("prize").toString())))
                    .playerWin(getMoney(new BigDecimal(hint.get("playerWin").toString())))
                    .playerPrize(getMoney(new BigDecimal(hint.get("playerPrize").toString())))
                    .effectiveAmount(getMoney(new BigDecimal(hint.get("effectiveAmount").toString())))
                    .win(getMoney(new BigDecimal(hint.get("win").toString())))
                    .waterFeeReal(getMoney(new BigDecimal(hint.get("waterFeeReal").toString())))
                    .amount(getMoney(new BigDecimal(hint.get("amount").toString())))
                    .companyWin(getMoney(new BigDecimal(hint.get("companyWin").toString())))
                    .totalWin(getMoney(new BigDecimal(hint.get("totalWin").toString())))
                    .waterFee(getMoney(new BigDecimal(hint.get("waterFee").toString())))
                    .validAmount(getMoney(new BigDecimal(hint.get("validAmount").toString())))
                    .build();
            list.add(sumMoneyReport);

        }
        return result;
    }
}
