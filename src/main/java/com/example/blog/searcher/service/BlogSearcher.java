package com.example.blog.searcher.service;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;

public interface BlogSearcher {
    BlogResponse search(BlogSearchRequest model);
    boolean isAvailable();
}
