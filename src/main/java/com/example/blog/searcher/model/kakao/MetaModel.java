package com.example.blog.searcher.model.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MetaModel {
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonProperty("pageable_count")
    private Integer pageableCount;
    @JsonProperty("is_end")
    private boolean isEnd;

}
