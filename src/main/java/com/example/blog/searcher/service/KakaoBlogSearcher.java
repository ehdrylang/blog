package com.example.blog.searcher.service;

import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import com.example.blog.searcher.model.kakao.DocumentModel;
import com.example.blog.searcher.model.kakao.KakaoResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Order(value = 1)
@RequiredArgsConstructor
@Slf4j
public class KakaoBlogSearcher implements BlogSearcher{
    private final WebClient kakaoWebClient;

    @Override
    @Async
    public BlogResponse search(BlogSearchRequest model) {
        Mono<KakaoResponseModel> mono = kakaoWebClient.get()
                .uri(createQueryString(model))
                .retrieve()
                .bodyToMono(KakaoResponseModel.class);
        KakaoResponseModel kakaoResponseModel = mono.block();
        log.info(kakaoResponseModel.toString());
        return kakaoResponseModel.toBlogResponse();
    }
    public String createQueryString(BlogSearchRequest model) {
        StringBuilder sb = new StringBuilder();
        sb.append("?query=")
                .append(model.getKeyword())
                .append("&sort=")
                .append(model.getSort().name())
                .append("&page=")
                .append(model.getPage())
                .append("&size=")
                .append(model.getSize());
        return sb.toString();
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
