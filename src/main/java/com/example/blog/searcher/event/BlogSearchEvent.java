package com.example.blog.searcher.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BlogSearchEvent {
    private String keyword;
    private LocalDateTime searchDate;
}
