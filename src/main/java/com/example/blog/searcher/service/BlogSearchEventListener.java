package com.example.blog.searcher.service;

import com.example.blog.searcher.event.BlogSearchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogSearchEventListener {
    private final List<BlogSearchEventConsumer> consumers;

    @Async
    @EventListener
    public void listen(BlogSearchEvent event) {
        for (BlogSearchEventConsumer processor : consumers) {
            processor.consume(event);
        }
    }
}
