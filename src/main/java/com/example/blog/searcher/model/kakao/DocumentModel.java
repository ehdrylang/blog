package com.example.blog.searcher.model.kakao;

import com.example.blog.searcher.model.BlogModel;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class DocumentModel {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private String datetime;

    @Builder
    public DocumentModel(String title, String contents, String url, String blogname, String thumbnail, String datetime) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogname = blogname;
        this.thumbnail = thumbnail;
        this.datetime = datetime;
    }

    public BlogModel toBlogModel() {
        LocalDateTime datetime = LocalDateTime.parse(this.datetime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return BlogModel.builder()
                .title(title)
                .contents(contents)
                .url(url)
                .blogName(blogname)
                .thumbnail(thumbnail)
                .datetime(datetime)
                .build();
    }
}
