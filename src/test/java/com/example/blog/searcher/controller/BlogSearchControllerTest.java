package com.example.blog.searcher.controller;

import com.example.blog.IntegrationTest;
import com.example.blog.searcher.constant.Constant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BlogSearchControllerTest extends IntegrationTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> operations;

    @BeforeEach
    public void init() {
        operations = redisTemplate.opsForZSet();
        operations.removeRange(Constant.SEARCH.name(), 0, Long.MAX_VALUE);
    }

    @AfterEach
    public void destroy() {
        operations.removeRange(Constant.SEARCH.name(), 0, Long.MAX_VALUE);
    }

    @Test
    @DisplayName("키워드 검색 횟수가 적으면 인기검색어는 10개 이하로 제공될 수 있다.")
    public void 키워드_자체가_적으면_인기검색어는_10개미만으로도_제공된다() throws Exception {
        operations.incrementScore(Constant.SEARCH.name(), "바다", 1.0);
        operations.incrementScore(Constant.SEARCH.name(), "해", 2.0);
        operations.incrementScore(Constant.SEARCH.name(), "사랑", 3.0);
        this.mvc.perform(get("/api/search/blog/keyword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keywords.*", hasSize(3)));

    }

    @Test
    @DisplayName("인기검색어는 가장 많이 검색된 순서대로 제공되어야 한다.")
    public void 인기검색어는_검색횟수가_가장많은_순서대로_제공되어야한다() throws Exception {
        operations.incrementScore(Constant.SEARCH.name(), "1", 1.0);
        operations.incrementScore(Constant.SEARCH.name(), "2", 2.0);
        operations.incrementScore(Constant.SEARCH.name(), "3", 3.0);
        operations.incrementScore(Constant.SEARCH.name(), "4", 4.0);
        operations.incrementScore(Constant.SEARCH.name(), "5", 5.0);
        operations.incrementScore(Constant.SEARCH.name(), "6", 6.0);
        operations.incrementScore(Constant.SEARCH.name(), "7", 7.0);
        operations.incrementScore(Constant.SEARCH.name(), "8", 8.0);
        operations.incrementScore(Constant.SEARCH.name(), "9", 9.0);
        operations.incrementScore(Constant.SEARCH.name(), "10", 10.0);
        operations.incrementScore(Constant.SEARCH.name(), "11", 11.0);
        operations.incrementScore(Constant.SEARCH.name(), "12", 12.0);
        this.mvc.perform(get("/api/search/blog/keyword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keywords[0].keyword", is("12")))
                .andExpect(jsonPath("$.keywords[0].count", is(12)))
                .andExpect(jsonPath("$.keywords[1].keyword", is("11")))
                .andExpect(jsonPath("$.keywords[1].count", is(11)))
                .andExpect(jsonPath("$.keywords[2].keyword", is("10")))
                .andExpect(jsonPath("$.keywords[2].count", is(10)))
                .andExpect(jsonPath("$.keywords[3].keyword", is("9")))
                .andExpect(jsonPath("$.keywords[3].count", is(9)))
                .andExpect(jsonPath("$.keywords[4].keyword", is("8")))
                .andExpect(jsonPath("$.keywords[4].count", is(8)))
                .andExpect(jsonPath("$.keywords[5].keyword", is("7")))
                .andExpect(jsonPath("$.keywords[5].count", is(7)))
                .andExpect(jsonPath("$.keywords[6].keyword", is("6")))
                .andExpect(jsonPath("$.keywords[6].count", is(6)))
                .andExpect(jsonPath("$.keywords[7].keyword", is("5")))
                .andExpect(jsonPath("$.keywords[7].count", is(5)))
                .andExpect(jsonPath("$.keywords[8].keyword", is("4")))
                .andExpect(jsonPath("$.keywords[8].count", is(4)))
                .andExpect(jsonPath("$.keywords[9].keyword", is("3")))
                .andExpect(jsonPath("$.keywords[9].count", is(3)));

    }


    @Test
    @DisplayName("검색된 키워드가 아무리 다양해도 최대 10개 까지만 인기검색어를 제공한다.")
    public void 인기검색어_최대10개() throws Exception {
        operations.incrementScore(Constant.SEARCH.name(), "1", 1.0);
        operations.incrementScore(Constant.SEARCH.name(), "2", 2.0);
        operations.incrementScore(Constant.SEARCH.name(), "3", 3.0);
        operations.incrementScore(Constant.SEARCH.name(), "4", 4.0);
        operations.incrementScore(Constant.SEARCH.name(), "5", 5.0);
        operations.incrementScore(Constant.SEARCH.name(), "6", 6.0);
        operations.incrementScore(Constant.SEARCH.name(), "7", 7.0);
        operations.incrementScore(Constant.SEARCH.name(), "8", 8.0);
        operations.incrementScore(Constant.SEARCH.name(), "9", 9.0);
        operations.incrementScore(Constant.SEARCH.name(), "10", 10.0);
        operations.incrementScore(Constant.SEARCH.name(), "11", 11.0);
        operations.incrementScore(Constant.SEARCH.name(), "12", 12.0);
        this.mvc.perform(get("/api/search/blog/keyword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keywords.*", hasSize(10)));
    }

}