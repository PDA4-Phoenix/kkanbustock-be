package com.bull4jo.kkanbustock.news.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NewsApiResponse {

    @JsonProperty("items")
    private List<NewsApiDetailResponse> newsApiDetailResponses;

}
