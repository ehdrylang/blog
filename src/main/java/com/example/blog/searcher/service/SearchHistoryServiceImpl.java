package com.example.blog.searcher.service;

import com.example.blog.searcher.domain.SearchHistory;
import com.example.blog.searcher.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService {
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
}
