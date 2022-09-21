package com.example.blog.searcher.service;

import com.example.blog.searcher.domain.SearchHistory;
import com.example.blog.searcher.event.BlogSearchEvent;
import com.example.blog.searcher.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchHistoryServiceImpl implements SearchHistoryService, BlogSearchEventConsumer {
    private final SearchHistoryRepository repository;

    @Override
    @Transactional
    public void save(String keyword, LocalDateTime searchDate) {
        SearchHistory history = SearchHistory.builder()
                .searchDate(searchDate)
                .keyword(keyword)
                .build();
        repository.save(history);
    }

    @Override
    @Transactional
    public void consume(BlogSearchEvent event) {
        if (!isValid(event)) {
            throw new IllegalArgumentException("BlogSearchEvent is invalid.");
        }
        save(event.getKeyword(), event.getSearchDate());
    }

    private boolean isValid(BlogSearchEvent event) {
        if (Objects.isNull(event)) {
            return false;
        }
        if (!StringUtils.hasText(event.getKeyword())) {
            return false;
        }
        if (Objects.isNull(event.getSearchDate())) {
            return false;
        }
        return true;
    }
}
