package com.example.blog.searcher.model;

import com.example.blog.searcher.domain.Sort;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
public class BlogSearchRequest {
    @NotBlank
    private String keyword;
    private Sort sort = Sort.RECENCY;
    @Min(1)
    @Max(50)
    private Integer page = 1;
    @Min(1)
    @Max(50)
    private Integer size = 10;
}
