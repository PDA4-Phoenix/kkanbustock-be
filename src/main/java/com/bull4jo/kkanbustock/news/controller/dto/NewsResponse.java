package com.bull4jo.kkanbustock.news.controller.dto;

import com.bull4jo.kkanbustock.news.domain.entity.News;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NewsResponse {
    private final String title;
    private final String link;
    private final String description;
    private final String pubDate;

    @Builder
    public NewsResponse(News news) {
        this.title = news.getTitle();
        this.link = news.getLink();
        this.description = news.getDescription();
        this.pubDate = news.getPubDate();
    }
}
