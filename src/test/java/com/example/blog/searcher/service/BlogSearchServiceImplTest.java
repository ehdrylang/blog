package com.example.blog.searcher.service;

import com.example.blog.MockTest;
import com.example.blog.searcher.domain.Sort;
import com.example.blog.searcher.event.BlogSearchEvent;
import com.example.blog.searcher.model.BlogModel;
import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class BlogSearchServiceImplTest extends MockTest {
    @InjectMocks
    private BlogSearchServiceImpl blogSearchService;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private BlogSearchCounter blogSearchCounter;
    @Spy
    private List<BlogSearcher> blogSearchers = new ArrayList<>();
    @Mock
    private KakaoBlogSearcher kakaoBlogSearcher;
    @Mock
    private NaverBlogSearcher naverBlogSearcher;

    private BlogSearchRequest request;

    @BeforeEach
    public void init() {
        blogSearchers.add(kakaoBlogSearcher);
        blogSearchers.add(naverBlogSearcher);

        request = new BlogSearchRequest();
        request.setKeyword("바다");
        request.setPage(1);
        request.setSize(10);
        request.setSort(Sort.RECENCY);
    }

    @Test
    @DisplayName("검색을 시도하면 검색 이벤트가 발생해야한다.")
    public void 검색시_이벤트_발생() {
        LocalDateTime now = LocalDateTime.now();
        BlogSearchEvent event = new BlogSearchEvent(request.getKeyword(), now);
        when(blogSearchCounter.count(request.getKeyword())).thenReturn(1.0);
        when(kakaoBlogSearcher.isAvailable()).thenReturn(true);
        CompletableFuture<BlogResponse> completableFuture = new CompletableFuture<>();
        ArrayList<BlogModel> blogModels = new ArrayList<>();
        blogModels.add(BlogModel.builder()
                .blogName("블로그네임")
                .title("블로그제목")
                .contents("블로그내용")
                .thumbnail("")
                .url("aa")
                .datetime(now)
                .build());
        BlogResponse blogResponse = BlogResponse.builder()
                .pageableCount(1)
                .totalCount(1)
                .items(blogModels)
                .build();
        completableFuture.complete(blogResponse);
        when(kakaoBlogSearcher.searchAsync(request)).thenReturn(completableFuture);
        // when
        blogSearchService.search(request);
        // then
        verify(publisher, times(1)).publishEvent(any(BlogSearchEvent.class));
    }

    @Test
    @DisplayName("검색을 시도하면 검색 횟수가 1 만큼 증가해야한다.")
    public void 검색시_키워드_검색_횟수_1만큼_증가() {
        // given

        // when
        blogSearchService.search(request);
    }
}