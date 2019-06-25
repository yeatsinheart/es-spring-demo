package com.example.esspringdemo.service.game.dao.commission;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dto.commission.ProxyUserType;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.betting.SumMoneyReport;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubProxyNum implements BaseDao {
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
        if (null != ((BettingArgs) args).getProxyId()) {
            select.must(QueryBuilders.termQuery("parent_id", ((BettingArgs) args).getProxyId()));
        }
        select.must(QueryBuilders.termQuery("user_type", ProxyUserType.PROXY.value()));
        select.mustNot(QueryBuilders.termQuery("id",((BettingArgs) args).getProxyId()));
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
