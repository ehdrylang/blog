package com.example.blog.searcher.domain;

import com.example.blog.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 키워드로 검색할 때마다 이력을 남기기 위한 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime searchDate;
    private String keyword;

    @Builder
    public SearchHistory(LocalDateTime searchDate, String keyword) {
        this.searchDate = searchDate;
        this.keyword = keyword;
    }
}
