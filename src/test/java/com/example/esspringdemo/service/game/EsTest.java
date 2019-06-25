package com.example.esspringdemo.service.game;

import com.alibaba.fastjson.JSON;
import com.example.esspringdemo.service.ReportService;
import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.dao.commission.*;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import com.example.esspringdemo.service.game.model.commission.BettingArgs;
import com.example.esspringdemo.service.game.model.commission.BonusArgs;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
    @Autowired
    ReportService gameservice;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test() {
        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch(DaoArgs.INDEX);//.setTypes("logs")

        BaseDao dao = new RechargeWithdraw();
        DaoArgs args = new BettingArgs();

        searchRequestBuilder.internalBuilder(dao.initTemple(args));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        DaoResult report = dao.get(args, response);
        System.out.println(JSON.toJSONString(report));
    }
}