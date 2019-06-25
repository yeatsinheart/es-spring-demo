package com.example.esspringdemo.service.game.dao.location;

import com.example.esspringdemo.service.game.dao.BaseDao;
import com.example.esspringdemo.service.game.model.DaoArgs;
import com.example.esspringdemo.service.game.model.DaoResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
* 原始模板/location/new
**/
public class New implements BaseDao {
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
        /*
        {
  "aggs": {
    "2": {
      "terms": {
        "field": "geoip.country_name",
        "size": 30,
        "order": {
          "1": "desc"
        }
      },
      "aggs": {
        "1": {
          "cardinality": {
            "field": "uuid",
            "precision_threshold": 1000
          }
        },
        "3": {

        }
      }
    }
  },

}

        */
        //    todo    select.must()
        select.must(QueryBuilders.termsQuery("event", Arrays.asList(100012)));
        select.must(QueryBuilders.termsQuery("new", Arrays.asList(1)));

        CardinalityBuilder uuid = AggregationBuilders.cardinality("uuid").field("uuid").precisionThreshold(1000);
        TermsBuilder city = AggregationBuilders.terms("city").field("geoip.city_name").size(30).order(Terms.Order.aggregation("1",false));
        city.subAggregation(uuid);
        CardinalityBuilder uuidFirst = AggregationBuilders.cardinality("uuidFirst").field("uuid").precisionThreshold(1000);
        TermsBuilder region = AggregationBuilders.terms("region").field("geoip.region_name").size(30).order(Terms.Order.aggregation("1",false));
        region.subAggregation(city).subAggregation(uuidFirst);

        CardinalityBuilder uuidParent = AggregationBuilders.cardinality("uuidParent").field("uuid").precisionThreshold(1000);
        TermsBuilder country = AggregationBuilders.terms("country").field("geoip.country_name").size(10).order(Terms.Order.aggregation("1",false));
        country.subAggregation(uuidParent).subAggregation(region);


        searchSourceBuilder.query(select).size(0).aggregation(country);
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
