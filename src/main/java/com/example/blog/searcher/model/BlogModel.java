package com.example.blog.searcher.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BlogModel {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;
    private LocalDateTime datetime;

    @Builder
    public BlogModel(String title, String contents, String url, String blogName, String thumbnail, LocalDateTime datetime) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogName = blogName;
        this.thumbnail = thumbnail;
        this.datetime = datetime;
    }
}
