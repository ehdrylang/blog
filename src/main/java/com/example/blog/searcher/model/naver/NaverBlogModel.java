package com.example.blog.searcher.model.naver;

import com.example.blog.searcher.model.BlogModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class NaverBlogModel {
    private String title;
    private String description;
    private String link;
    private String bloggername;
    private String bloggerlink;
    private String postdate;

    public BlogModel toBlogModel() {
        LocalDate localDate = LocalDate.parse(postdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return BlogModel.builder()
                .title(title)
                .blogName(bloggername)
                .contents(description)
                .url(link)
                .thumbnail("")
                .datetime(LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0))
                .build();
    }
}
