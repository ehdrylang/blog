package com.example.blog.searcher.service;

import com.example.blog.MockTest;
import com.example.blog.searcher.domain.Sort;
import com.example.blog.searcher.event.BlogSearchEvent;
import com.example.blog.searcher.exception.NoSearchSystemException;
import com.example.blog.searcher.exception.ThirdPartyException;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    public void search_검색시_이벤트_발생() {
        LocalDateTime now = LocalDateTime.now();
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
    @DisplayName("검색을 시도하면 검색 횟수가 증가해야한다.")
    public void search_검색시_키워드_검색_횟수_증가() {
        LocalDateTime now = LocalDateTime.now();
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
        verify(blogSearchCounter, times(1)).count(request.getKeyword());
    }

    @Test
    @DisplayName("검색 소스가 없어서 조회할 수 없는 경우 NoSearchSystemException 발생")
    public void search_조회할_방법이_없는_경우_NoSearchSystemException_발생() {
        when(blogSearchCounter.count(request.getKeyword())).thenReturn(1.0);
        when(kakaoBlogSearcher.isAvailable()).thenReturn(false);
        when(naverBlogSearcher.isAvailable()).thenReturn(false);
        // when then
        assertThatThrownBy(() -> blogSearchService.search(request))
                .isInstanceOf(NoSearchSystemException.class);
    }

    @Test
    @DisplayName("검색을 시도하면 검색 소스를 찾아서 요청해야한다.")
    public void search_검색시_소스_요청() {
        LocalDateTime now = LocalDateTime.now();
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
        verify(kakaoBlogSearcher, times(1)).searchAsync(request);
    }

    @Test
    @DisplayName("검색 요청 중에 예외가 발생하여 응답할 수 없는 경우, ThirdPartyException이 발생")
    public void 외부소스_요청중_예외발생하면_ThirdPartyException_변환하여_던진다() {
        LocalDateTime now = LocalDateTime.now();
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
        when(kakaoBlogSearcher.searchAsync(request)).thenThrow(new RuntimeException());
        // when then
        assertThatThrownBy(() -> blogSearchService.search(request))
                .isInstanceOf(ThirdPartyException.class);
    }

}