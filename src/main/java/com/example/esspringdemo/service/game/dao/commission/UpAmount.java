package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;

public class UpAmount implements BaseDao {
    /**
     * 生成查询对象
     * 服务类调用searchRequestBuilder.internalBuilder(SearchSourceBuilder sourceBuilder) 设置查询内容 获得响应内容
     *
     * @param args
     */
    @Override
    public SearchSourceBuilder initTemple(DaoArgs args) {
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource();

        SumBuilder sumAmount = AggregationBuilders.sum("sumAmount").field("1015.amount");
        SumBuilder sumRebate = AggregationBuilders.sum("sumRebate").field("1015.rebate");
        BoolQueryBuilder select = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("event", 1015))
                .must(QueryBuilders.termQuery("1015.status", 1));
        baseBoolQuery(select, args);
        select.must(QueryBuilders.termQuery("parentId", ((BettingArgs) args).getProxyId()));
        searchSourceBuilder.aggregation(sumAmount).aggregation(sumRebate).size(0).query(select);
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
        List<SumMoneyReport> list = new ArrayList<>();


        result.setResult(list);
        return result;
    }
}
