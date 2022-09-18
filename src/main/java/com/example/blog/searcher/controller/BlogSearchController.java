package com.example.blog.searcher.controller;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
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

    @GetMapping("")
    public ResponseEntity<BlogResponse> search(@Validated BlogSearchRequest blogSearchRequest) {
        BlogResponse response = blogSearchService.search(blogSearchRequest);
        return ResponseEntity.ok(response);
    }
}
