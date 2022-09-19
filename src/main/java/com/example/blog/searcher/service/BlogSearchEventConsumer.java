package com.example.blog.searcher.service;

import com.example.blog.searcher.event.BlogSearchEvent;

public interface BlogSearchEventConsumer {
    public void consume(BlogSearchEvent event);
}
