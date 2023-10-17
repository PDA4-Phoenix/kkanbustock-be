package com.bull4jo.kkanbustock.news.service;

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
        saveNews(); // 추후 삭제
        Page<News> news = newsRepository.findAll(pageable);

        return news
                .getContent()
                .stream()
                .map(NewsResponse::new)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void saveNews() { // 스케줄러로 매일 자정마다 알아서 실행되도록 설정

        // 메서드 실행 시작 로그
        System.out.println("saveNews() 메서드가 실행됩니다.");

        newsRepository.deleteAll(); // 추후 삭제

        String keyword = "주식";
        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + keyword + "&sort=sim"; // 정확도순

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.set("X-Naver-Client-Id", clientId);
        header.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity entity = new HttpEntity<>(header);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(apiURL);
        URI finalUri = uri.build().encode().toUri();
        System.out.println(finalUri);

        NewsApiResponse response = restTemplate.exchange(finalUri, HttpMethod.GET, entity, NewsApiResponse.class).getBody();
        System.out.println("@@@@@@" + response.getNewsApiDetailResponses().get(0).getLink());

        for (int i = 0; i < response.getNewsApiDetailResponses().size(); i++) {
            String title = replaceText(response.getNewsApiDetailResponses().get(i).getTitle());
            String link = response.getNewsApiDetailResponses().get(i).getLink();
            String description = replaceText(response.getNewsApiDetailResponses().get(i).getDescription());
            String pubDate = response.getNewsApiDetailResponses().get(i).getPubDate();

            News news = News
                    .builder()
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