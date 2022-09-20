package com.example.blog.searcher.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
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
