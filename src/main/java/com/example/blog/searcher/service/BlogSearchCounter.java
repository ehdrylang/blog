package com.example.blog.searcher.service;

import com.example.blog.searcher.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogSearchCounter {
    private RedisTemplate<String, String> redisTemplate;

    public Double count(String keyword) {
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        return operations.incrementScore(Constant.SEARCH.name(), keyword, 1.0);
    }
}
