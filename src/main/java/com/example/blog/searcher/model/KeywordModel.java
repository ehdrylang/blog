package com.example.blog.searcher.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordModel {
    private String keyword;
    private int count;

    @Builder
    public KeywordModel(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }
}
