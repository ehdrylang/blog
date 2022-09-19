package com.example.blog.searcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Value("${search.blog.kakao.url}")
    private String apiUrl;
    @Value("${search.blog.kakao.apiKey}")
    private String apiKey;

    private static final String HEADER_PREFIX = "KakaoAK ";

    @Bean
    public WebClient kakaoWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, HEADER_PREFIX + apiKey)
                .baseUrl(apiUrl)
                .build();
    }
}
