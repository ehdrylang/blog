package com.example.blog.searcher.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogResponse {
    private int totalCount;
    private int pageableCount;
    private List<BlogModel> items;

    @Builder
    public BlogResponse(int totalCount, int pageableCount, List<BlogModel> items) {
        this.totalCount = totalCount;
        this.pageableCount = pageableCount;
        this.items = items;
    }
}
