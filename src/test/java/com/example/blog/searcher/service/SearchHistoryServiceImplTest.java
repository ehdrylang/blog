package com.example.blog.searcher.service;

import com.example.blog.MockTest;
import com.example.blog.searcher.domain.SearchHistory;
import com.example.blog.searcher.event.BlogSearchEvent;
import com.example.blog.searcher.repository.SearchHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchHistoryServiceImplTest extends MockTest {
    @InjectMocks
    private SearchHistoryServiceImpl searchHistoryService;
    @Mock
    private SearchHistoryRepository repository;

    @Test
    @DisplayName("검색 이벤트가 발생하면 이력을 저장해야한다.")
    public void 검색이벤트_발생시_이력_저장() {
        // given
        BlogSearchEvent event = new BlogSearchEvent("바다", LocalDateTime.now());
        SearchHistory history = SearchHistory.builder()
                .keyword(event.getKeyword())
                .searchDate(event.getSearchDate())
                .build();
        when(repository.save(any())).thenReturn(history);
        // when
        searchHistoryService.consume(event);

        verify(repository, times(1)).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    @DisplayName("검색이벤트 발생했으나 키워드가 비어있을 경우 예외 IllegalArgumentException이 발생한다.")
    public void 키워드_유효성검사_빈문자열(String keyword) {
        // given
        BlogSearchEvent event = new BlogSearchEvent(keyword, LocalDateTime.now());
        // when then
        assertThatThrownBy(() -> searchHistoryService.consume(event))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("검색이벤트 발생했으나 키워드가 null인 경우 예외 IllegalArgumentException이 발생한다.")
    public void 키워드_유효성검사_NULL() {
        // given
        BlogSearchEvent event = new BlogSearchEvent(null, LocalDateTime.now());
        // when then
        assertThatThrownBy(() -> searchHistoryService.consume(event))
                .isInstanceOf(IllegalArgumentException.class);

    }
    @Test
    @DisplayName("검색이벤트 발생했으나 이벤트 발생시간이 null인 경우 예외 IllegalArgumentException이 발생한다.")
    public void 이벤트발생시간_유효성검사_NULL() {
        // given
        BlogSearchEvent event = new BlogSearchEvent("keyword", null);
        // when then
        assertThatThrownBy(() -> searchHistoryService.consume(event))
                .isInstanceOf(IllegalArgumentException.class);

    }
}