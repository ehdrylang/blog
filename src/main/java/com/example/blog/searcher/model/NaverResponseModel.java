package com.example.blog.searcher.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class NaverResponseModel {
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverBlogModel> items;

    public BlogResponse toBlogResponse() {
        List<BlogModel> result;
        if (items == null || items.isEmpty()) {
            result = new ArrayList<>();
        } else {
            result = items.stream()
                    .map(NaverBlogModel::toBlogModel)
                    .collect(Collectors.toList());
        }
        return BlogResponse.builder()
                .totalCount(total)
                .pageableCount(start)
                .items(result)
                .build();
    }
}
