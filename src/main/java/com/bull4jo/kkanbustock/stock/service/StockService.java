package com.bull4jo.kkanbustock.stock.service;

import com.bull4jo.kkanbustock.stock.domain.MarketType;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.repository.StockRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 주식 넣고, 빼고, 업데이트, 가져오고, 있는지 확인하고
@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final String dartUrl = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
    private final String authKey = "IDHVrjX6z8KgvQCdBokJywDgdQjpPnlGVoqDPNmuz5RcZthIW+GAQmSYfzT/n+Um2jMkMly1jI2eEJWKqO4C0g==";
    @Transactional
    public String save(final Stock stock) {
        return stockRepository
                .save(stock)
                .getSrtnCd();
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void setInit() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(dartUrl).newBuilder();

        // 메서드 실행 시작 로그
        System.out.println("setInit() 메서드가 실행됩니다.");

        urlBuilder.addQueryParameter("serviceKey", authKey);
        urlBuilder.addQueryParameter("resultType", "json");
        urlBuilder.addQueryParameter("numOfRows", "2420021");

        String urlWithParam = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(urlWithParam)
                .method("GET", null)
                .build();

        Stock stock = null;
        try {
            // API 호출 및 응답 받기
            Response response = client.newCall(request).execute();

            // 응답이 성공인 경우
            if (response.isSuccessful()) {
                // 응답 처리 로직
                String responseBody = response.body().string();
//                System.out.println("API 응답: " + responseBody);
                parseResponses(responseBody);
            } else {
                // 응답이 실패인 경우
                throw new RuntimeException("API 요청 실패. 응답 코드: " + response.code());
            }

            // 응답을 close
            response.close();

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
    }

    @Transactional
    public Stock findByStrnCd(final String strnCd) {
        Optional<Stock> byItmsNm = stockRepository.findById(strnCd);
        if (byItmsNm.isEmpty()) {
            Stock stock = getFromDartByStrnCd(strnCd).orElseThrow();
            stockRepository.save(stock);
            System.out.println("id from Dart");
            return stock;
        }
        System.out.println("id from Repo");
        return byItmsNm.get();
    }

    @Transactional
    public Stock findByItmsNm(final String itmsNm) {
        Optional<Stock> byItmsNm = stockRepository.findStockByItmsNm(itmsNm);
        if (byItmsNm.isEmpty()) {
            Stock stock = getFromDartByItmsNm(itmsNm).orElseThrow();
            System.out.println("name from Dart");
            stockRepository.save(stock);
            return stock;
        }
        System.out.println("name from Repo");
        return byItmsNm.get();
    }

    private Optional<Stock> getFromDartByStrnCd(final String srtnCd) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(dartUrl).newBuilder();

        urlBuilder.addQueryParameter("serviceKey", authKey);
        urlBuilder.addQueryParameter("resultType", "json");
        urlBuilder.addQueryParameter("likeSrtnCd", srtnCd);
        urlBuilder.addQueryParameter("numOfRows", "1");
        String urlWithParam = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(urlWithParam)
                .method("GET", null)
                .build();

        Stock stock = null;
        try {
            // API 호출 및 응답 받기
            Response response = client.newCall(request).execute();

            // 응답이 성공인 경우
            if (response.isSuccessful()) {
                // 응답 처리 로직
                String responseBody = response.body().string();
//                System.out.println("API 응답: " + responseBody);
                stock = parseResponse(responseBody);
            } else {
                // 응답이 실패인 경우
                throw new RuntimeException("API 요청 실패. 응답 코드: " + response.code());
            }

            // 응답을 close
            response.close();

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
        return Optional.of(stock);
    }

    private Optional<Stock> getFromDartByItmsNm(final String itmsNm) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(dartUrl).newBuilder();

        urlBuilder.addQueryParameter("serviceKey", authKey);
        urlBuilder.addQueryParameter("resultType", "json");
        urlBuilder.addQueryParameter("itmsNm", itmsNm);
        urlBuilder.addQueryParameter("numOfRows", "1");
        String urlWithParam = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(urlWithParam)
                .method("GET", null)
                .build();

        Stock stock = null;
        try {
            // API 호출 및 응답 받기
            Response response = client.newCall(request).execute();

            // 응답이 성공인 경우
            if (response.isSuccessful()) {
                // 응답 처리 로직
                String responseBody = response.body().string();
//                System.out.println("API 응답: " + responseBody);
                stock = parseResponse(responseBody);
            } else {
                // 응답이 실패인 경우
                throw new RuntimeException("API 요청 실패. 응답 코드: " + response.code());
            }

            // 응답을 close
            response.close();

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
        return Optional.of(stock);
    }

    public Stock parseResponse(String jsonResponse) {
        // Parse the JSON response
        JsonObject responseJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject response = responseJson.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");
        JsonObject item = items.getAsJsonArray("item").get(0).getAsJsonObject();

        Stock stock = Stock.builder()
                .srtnCd(item.get("srtnCd").getAsString())
                .itmsNm(item.get("itmsNm").getAsString())
                .mrktCtg(MarketType.valueOf(item.get("mrktCtg").getAsString()))
                .clpr(item.get("clpr").getAsInt())
                .mkp(item.get("mkp").getAsInt())
                .basDt(item.get("basDt").getAsInt())
                .build();
        return stock;
    }

    public void parseResponses(String jsonResponse) {
        // Parse the JSON response
        JsonObject responseJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject response = responseJson.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");

        JsonArray item = items.getAsJsonArray("item");
        List<Stock> list = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            JsonObject asJsonObject = item.get(i).getAsJsonObject();

            Stock stock = Stock.builder()
                    .srtnCd(asJsonObject.get("srtnCd").getAsString())
                    .itmsNm(asJsonObject.get("itmsNm").getAsString())
                    .mrktCtg(MarketType.valueOf(asJsonObject.get("mrktCtg").getAsString()))
                    .clpr(asJsonObject.get("clpr").getAsInt())
                    .mkp(asJsonObject.get("mkp").getAsInt())
                    .basDt(asJsonObject.get("basDt").getAsInt())
                    .build();
            System.out.println(stock.getItmsNm());
            list.add(stock);


        }
        stockRepository.saveAll(list);
        System.out.println("save fin");
    }

    @Transactional
    public String update(final String strnCd, final Stock req) {
        Stock stock = stockRepository.findById(strnCd).orElseThrow();
        stock.update(req.getClpr(), req.getMkp());
        return stock.getSrtnCd();
    }

    @Transactional
    public String delete(final String strnCd) {
        Stock stock = stockRepository.findById(strnCd).orElseThrow();
        stockRepository.delete(stock);
        return stock.getSrtnCd();
    }

    @Transactional
    public String deleteByItmsNm(final String itmsNm) {
        Stock stock = stockRepository.findStockByItmsNm(itmsNm).orElseThrow();
        stockRepository.delete(stock);
        return stock.getSrtnCd();
    }
}
