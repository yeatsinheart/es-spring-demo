package com.example.esspringdemo.service.game.dao.detail;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/detail/firstRechargeAll
**/
public class FirstRechargeAll implements BaseDao {
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
        select.should(QueryBuilders.termsQuery("1000.first", Arrays.asList(1)))
                .should(QueryBuilders.termsQuery("1015.first", Arrays.asList(1)));
        select.minimumNumberShouldMatch(1).adjustPureNegative(true).boost(1);
        // todo select.must();
        // todo select..filter(QueryBuilders.termsQuery("event", Arrays.asList()));
        //todo  select.should(QueryBuilders.termsQuery("1000.first", Arrays.asList(1)))
        //                .should(QueryBuilders.termsQuery("1015.first", Arrays.asList(1)));

        searchSourceBuilder.query(select).fields(Arrays.asList("userName","parentName","uuid","event","@timestamp","1000.amount","1015.amount"))
                .sort(SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC));
        //todo searchSourceBuilder.from();
        //todo searchSourceBuilder.size();
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
