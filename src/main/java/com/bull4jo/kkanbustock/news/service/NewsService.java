package com.bull4jo.kkanbustock.news.service;

import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.news.controller.dto.NewsApiDetailResponse;
import com.bull4jo.kkanbustock.news.controller.dto.NewsApiResponse;
import com.bull4jo.kkanbustock.news.controller.dto.NewsResponse;
import com.bull4jo.kkanbustock.news.domain.entity.News;
import com.bull4jo.kkanbustock.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Value("${naver.clientId}")
    String clientId;

    @Value("${naver.clientSecret}")
    String clientSecret;

    public List<NewsResponse> getNews(Pageable pageable) {

        // saveNews(); // 추후 삭제
        Page<News> news = newsRepository.findAll(pageable);

        return news
                .getContent()
                .stream()
                .map(NewsResponse::new)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void saveNews() { // 스케줄러로 매일 자정마다 알아서 실행되도록 설정
        List<String> keywords = Arrays.asList("주식", "특징주", "환율", "금리", "주가");
        for (String keyword : keywords) {
            saveKeywordNews(keyword);
        }
    }

    private void saveKeywordNews(String keyword) {

        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + keyword + "&display=100&sort=sim"; // 정확도순

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.set("X-Naver-Client-Id", clientId);
        header.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity entity = new HttpEntity<>(header);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(apiURL);
        URI finalUri = uri.build().encode().toUri();

        NewsApiResponse response = restTemplate.exchange(finalUri, HttpMethod.GET, entity, NewsApiResponse.class).getBody();

        if (response == null) {
            throw new CustomException(ErrorCode.NEWS_NOT_FOUND);
        }

        for (NewsApiDetailResponse apiDetailResponse : response.getNewsApiDetailResponses()) {
            String title = replaceText(apiDetailResponse.getTitle());
            String link = apiDetailResponse.getLink();
            String description = replaceText(apiDetailResponse.getDescription());
            String pubDate = apiDetailResponse.getPubDate();

            News news = News.builder()
                    .title(title)
                    .link(link)
                    .description(description)
                    .pubDate(pubDate)
                    .build();

            newsRepository.save(news);
        }
    }

    public static String replaceText(String text){
        return text.replace("<b>", "")
                .replace("</b>", "")
                .replace("&quot;", "\"");
    }

}