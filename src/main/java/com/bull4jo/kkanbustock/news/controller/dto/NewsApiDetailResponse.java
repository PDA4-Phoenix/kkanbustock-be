package com.bull4jo.kkanbustock.news.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class NewsApiDetailResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pubDate")
    private String pubDate;

}
