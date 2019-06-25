package com.example.esspringdemo.service.game;

import com.example.esspringdemo.service.ReportService;
import org.elasticsearch.action.search.SearchResponse;

public class WithdrawService  extends ElasticSearchService implements ReportService {

    @Override
    public SearchResponse call() {
        return null;
    }
}
