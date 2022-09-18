package com.example.blog.searcher.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordRankResponse {
    private List<KeywordModel> keywords;

    @Builder
    public KeywordRankResponse(List<KeywordModel> keywords) {
        this.keywords = keywords;
    }
}
