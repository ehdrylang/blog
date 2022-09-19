package com.example.blog.searcher.controller;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import com.example.blog.searcher.model.KeywordRankResponse;
import com.example.blog.searcher.service.BlogKeywordService;
import com.example.blog.searcher.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/blog")
@Slf4j
public class BlogSearchController {
    private final BlogSearchService blogSearchService;
    private final BlogKeywordService blogKeywordService;

    /**
     * 키워드로 블로그 검색하는 메서드
     */
    @GetMapping("")
    public ResponseEntity<BlogResponse> search(@Validated BlogSearchRequest blogSearchRequest) {
        BlogResponse response = blogSearchService.search(blogSearchRequest);
        log.info("controller !!! {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * 블로그 인기 검색어 TOP 10 키워드와 검색 횟수를 조회하는 메서드
     */
    @GetMapping("/keyword")
    public ResponseEntity<KeywordRankResponse> getTopKeyword() {
        KeywordRankResponse topKeyword = blogKeywordService.getTopKeyword();
        return ResponseEntity.ok(topKeyword);
    }
}
