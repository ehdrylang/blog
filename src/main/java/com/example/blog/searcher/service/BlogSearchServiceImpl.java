package com.example.blog.searcher.service;

import com.example.blog.common.exception.ErrorCode;
import com.example.blog.searcher.constant.Constant;
import com.example.blog.searcher.event.BlogSearchEvent;
import com.example.blog.searcher.exception.NoSearchSystemException;
import com.example.blog.searcher.exception.ThirdPartyException;
import com.example.blog.searcher.model.BlogResponse;
import com.example.blog.searcher.model.BlogSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class BlogSearchServiceImpl implements BlogSearchService{
    private final ApplicationEventPublisher publisher;
    private final List<BlogSearcher> blogSearchers;
    private final BlogSearchCounter blogSearchCounter;

    @Override
    public BlogResponse search(BlogSearchRequest model) {
        // 검색 요청이 발생했다는 이벤트 발생
        publisher.publishEvent(new BlogSearchEvent(model.getKeyword(), LocalDateTime.now()));

        // 검색 횟수 증가
        blogSearchCounter.count(model.getKeyword());

        // 검색 서비스에 요청
        BlogSearcher blogSearcher = blogSearchers.stream()
                .filter(BlogSearcher::isAvailable)
                .findFirst()
                .orElseThrow(() -> new NoSearchSystemException(ErrorCode.NO_SEARCH_SYSTEM_EXCEPTION));
        Future<BlogResponse> future = blogSearcher.searchAsync(model);
        try {
            return future.get();
        } catch (Exception e) {
            // 예외 변환
            throw new ThirdPartyException(e, ErrorCode.THIRD_PARTY_ERROR);
        }
    }
}
