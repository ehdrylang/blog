package com.example.blog.searcher.service;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;

public interface BlogSearchService {
    BlogResponse search(BlogSearchRequest model);
}
