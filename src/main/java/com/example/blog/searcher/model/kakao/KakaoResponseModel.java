package com.example.blog.searcher.model.kakao;

import com.example.blog.searcher.model.BlogModel;
import com.example.blog.searcher.model.BlogResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class KakaoResponseModel {
    private MetaModel meta;
    private List<DocumentModel> documents;

    @Builder
    public KakaoResponseModel(MetaModel meta, List<DocumentModel> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public BlogResponse toBlogResponse() {
        if (meta == null || documents == null || documents.isEmpty()) {
            return BlogResponse.builder()
                    .items(new ArrayList<>())
                    .totalCount(0)
                    .pageableCount(1)
                    .build();
        }
        List<BlogModel> blogModels = documents.stream()
                .map(DocumentModel::toBlogModel)
                .collect(Collectors.toList());
        return BlogResponse.builder()
                .totalCount(meta.getTotalCount())
                .pageableCount(meta.getPageableCount())
                .items(blogModels)
                .build();
    }
}
