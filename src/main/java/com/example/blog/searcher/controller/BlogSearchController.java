package com.example.blog.searcher.controller;

import com.example.blog.searcher.domain.Sort;
import com.example.blog.searcher.model.BlogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/blog")
@Slf4j
@Validated
public class BlogSearchController {

    @GetMapping("")
    public ResponseEntity<BlogResponse> search(@RequestParam @NotBlank String keyword,
                                               @RequestParam(required = false, defaultValue = "RECENCY") Sort sort,
                                               @RequestParam(required = false, defaultValue = "1") @Min(1) @Max(50) Integer page,
                                               @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(50) Integer size) {
        log.info("keyword={}, sort={}, page={}, size={}", keyword, sort, page, size);
        return ResponseEntity.ok(null);
    }
}
