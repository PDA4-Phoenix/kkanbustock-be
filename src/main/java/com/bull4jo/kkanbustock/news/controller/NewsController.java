package com.bull4jo.kkanbustock.news.controller;

import com.bull4jo.kkanbustock.news.controller.dto.NewsResponse;
import com.bull4jo.kkanbustock.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/v1/news")
    public ResponseEntity<List<NewsResponse>> getNews(
            @RequestParam(value = "page", defaultValue = "0") int page, // 호출 횟수
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getNews(pageable));
    }
}
