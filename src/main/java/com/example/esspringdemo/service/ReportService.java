package com.example.esspringdemo.service;

import org.elasticsearch.action.search.SearchResponse;

public interface ReportService {
    SearchResponse call();
}
