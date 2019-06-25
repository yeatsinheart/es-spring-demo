package com.example.esspringdemo.service.game;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class ElasticSearchService {
    @Resource
     ElasticsearchTemplate elasticsearchTemplate;
}
