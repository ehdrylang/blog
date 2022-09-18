package com.example.blog.searcher.service;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class KakaoBlogSearcher implements BlogSearcher{
    @Value("${search.blog.kakao.url}")
    private String apiUrl;
    @Value("${search.blog.kakao.apiKey}")
    private String apiKey;

    @Override
    @Async
    public BlogResponse search(BlogSearchRequest model) {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}
