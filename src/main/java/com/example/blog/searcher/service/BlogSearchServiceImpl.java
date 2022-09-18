package com.example.blog.searcher.service;

import com.example.blog.common.exception.ErrorCode;
import com.example.blog.searcher.exception.NoSearchSystemException;
import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogSearchServiceImpl implements BlogSearchService{
    private final List<BlogSearcher> blogSearchers;

    @Override
    public BlogResponse search(BlogSearchRequest model) {
        // 검색 서비스에 요청
        BlogSearcher blogSearcher = blogSearchers.stream()
                .filter(BlogSearcher::isAvailable)
                .findFirst()
                .orElseThrow(() -> new NoSearchSystemException(ErrorCode.NO_SEARCH_SYSTEM_EXCEPTION));


        return blogSearcher.search(model);
    }
}
