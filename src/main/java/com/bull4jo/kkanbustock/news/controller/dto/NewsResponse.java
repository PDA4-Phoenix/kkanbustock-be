package com.bull4jo.kkanbustock.news.controller.dto;

import com.bull4jo.kkanbustock.news.domain.entity.News;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewsResponse {
    private final Long id;
    private final String title;
    private final String link;
    private final String description;
    private final String pubDate;

    @Builder
    public NewsResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.link = news.getLink();
        this.description = news.getDescription();
        this.pubDate = news.getPubDate();
    }
}
