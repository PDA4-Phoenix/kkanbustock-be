package com.bull4jo.kkanbustock.news.controller;

import com.bull4jo.kkanbustock.news.controller.dto.NewsResponse;
import com.bull4jo.kkanbustock.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/v1/news")
    public ResponseEntity<List<NewsResponse>> getNews(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "size") int size
    ) {
        return ResponseEntity.ok(newsService.getNews(keyword, size));
    }
}
