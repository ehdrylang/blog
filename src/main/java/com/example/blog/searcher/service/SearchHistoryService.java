package com.example.blog.searcher.service;

import java.time.LocalDateTime;

public interface SearchHistoryService {
    void save(String keyword, LocalDateTime searchDate);
}
