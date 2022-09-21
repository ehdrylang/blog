package com.example.blog.searcher.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BlogSearchEvent {
    private String keyword;
    private LocalDateTime searchDate;
}
