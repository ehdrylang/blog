package com.example.blog.searcher.service;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;

import java.util.concurrent.Future;

public interface BlogSearcher {
    BlogResponse search(BlogSearchRequest model);
    boolean isAvailable();
    default Future<BlogResponse> searchAsync(BlogSearchRequest model) {
        return null;
    }
}
