package com.example.blog.searcher.service;

import com.example.blog.searcher.constant.Constant;
import com.example.blog.searcher.model.KeywordModel;
import com.example.blog.searcher.model.KeywordRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogKeywordServiceImpl implements BlogKeywordService{
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 가장 많이 검색된 키워드 최대 10개를 제공하는 메서드
     */
    @Override
    public KeywordRankResponse getTopKeyword() {
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        List<KeywordModel> topKeywords = Objects.requireNonNull(operations.reverseRangeWithScores(Constant.SEARCH.name(), 0, 9))
                .stream()
                .map(it -> convert(it.getValue(), it.getScore()))
                .collect(Collectors.toList());
        return KeywordRankResponse.builder()
                .keywords(topKeywords)
                .build();
    }

    private KeywordModel convert(String keyword, Double score) {
        int count = Objects.requireNonNull(score).intValue();
        return KeywordModel.builder()
                .keyword(keyword)
                .count(count)
                .build();
    }
}
