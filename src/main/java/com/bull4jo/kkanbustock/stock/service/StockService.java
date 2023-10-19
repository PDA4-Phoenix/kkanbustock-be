package com.bull4jo.kkanbustock.stock.service;

import com.bull4jo.kkanbustock.common.ParseStockJsonResponse;
import com.bull4jo.kkanbustock.common.RandomNumberGenerator;
import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.member.domain.entity.InvestorType;
import com.bull4jo.kkanbustock.stock.domain.RecommendStock;
import com.bull4jo.kkanbustock.stock.domain.RecommendStockResponse;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.repository.RecommendStockRepository;
import com.bull4jo.kkanbustock.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

// 주식 넣고, 빼고, 업데이트, 가져오고, 있는지 확인하고
@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final RecommendStockRepository recommendStockRepository;
    private final String dartUrl = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
    private final String authKey = "IDHVrjX6z8KgvQCdBokJywDgdQjpPnlGVoqDPNmuz5RcZthIW+GAQmSYfzT/n+Um2jMkMly1jI2eEJWKqO4C0g==";

    private final String shinhanUrl = "https://gapi.shinhansec.com:8443/openapi/v1.0/recommend/portfolio";
    private final String apiKey = "l7xxR7Fe0dP3i8KPZaPKpknI2vWrMeJfwDpk";
    private final String[] comments = {
            "회사의 안정적인 재무 상태와 지속적인 수익성은 투자자들에게 신뢰감을 주고 있습니다.",
            "최근에 발표된 재무 보고서에서 매출액이 꾸준히 상승하고 있으며, 순이익 역시 꾸준한 성장을 보이고 있습니다.",
            "회사의 혁신적인 제품 라인업과 서비스는 시장에서 큰 인기를 얻고 있으며 미래 성장 가능성이 높습니다.",
            "주식의 시가총액은 산업 평균을 넘어서며, 회사의 경쟁력을 반영하고 있습니다.",
            "투자자들에게 배당금을 제공하며, 안정적인 배당금 정책을 유지하고 있어 수익 투자자에게 매력적입니다.",
            "회사는 지속가능한 비즈니스 모델과 환경, 사회, 지배구조 (ESG)에 대한 강한 헌신을 보이고 있습니다.",
            "주식의 기술적 분석을 보면, 매수 신호가 나오고 있어 기술적으로도 유망해 보입니다.",
            "회사의 경영진은 업계에서 평가가 높으며, 회사의 성장을 주도할 능력을 갖추고 있습니다.",
            "산업 동향에 따르면 해당 분야가 성장 중이며, 이 회사는 이 성장에 크게 기여할 것으로 예상됩니다.",
            "회사의 지속적인 연구 개발 노력은 혁신적 제품과 서비스의 출시를 촉진하고 있습니다.",
            "주식의 주가 변동성이 낮아서, 안정적인 투자 기회를 제공하고 있습니다.",
            "회사는 시장에서 강력한 경쟁우위를 갖고 있으며, 시장 점유율을 지속적으로 확대하고 있습니다.",
            "주식은 분산 투자 포트폴리오에 투자하기에 이상적한 선택지입니다.",
            "최근에 발표된 신제품 또는 서비스가 긍정적인 소비자 반응을 얻었으며, 수익성을 높일 것으로 전망됩니다.",
            "회사는 혁신적인 기술과 디지털 전환을 통해 비즈니스 모델을 개선하고 있습니다.",
            "주식은 배당금 지급 뿐만 아니라 주가 상승 잠재력을 가진 '성장 + 가치' 투자의 조화를 이루고 있습니다.",
            "회사의 글로벌 프레젠스는 세계 시장에서의 성장 기회를 확대하고 있습니다.",
            "주식은 경쟁사에 비해 저평가되어 있으며, 잠재적으로 큰 가치를 창출할 수 있습니다.",
            "회사는 코로나19 팬데믹과 같은 급격한 변화에도 민첩하게 대응하고, 비즈니스의 지속가능성을 입증하였습니다.",
            "주식의 고정 비용 구조와 높은 마진은 수익성을 높이는데 기여하고 있습니다.",
            "회사의 경영진은 투명성과 효율성을 추구하며, 투자자들에게 신뢰를 주고 있습니다.",
            "주식은 성과를 중시하고 지속가능한 미래 성장에 초점을 맞추고 있습니다.",
            "회사는 글로벌 경제 상황에도 불구하고 안정적인 성과를 유지하고 있습니다.",
            "향후 수익성이 높을 것으로 예상되는 새로운 시장 진출 기회가 있습니다.",
            "주식의 주가-수익비율 (P/E ratio)은 업계 평균보다 낮아서 가치 투자의 기회를 제공합니다.",
            "회사의 브랜드는 고객들에게 강한 신뢰를 받고 있으며, 브랜드 가치가 증가하고 있습니다.",
            "회사는 지속가능한 환경 및 사회적 책임을 향한 커밋먼트를 강조하고 있습니다.",
            "주식의 배당 수익률은 투자자에게 안정적인 현금 흐름을 제공합니다.",
            "회사는 효율적인 비즈니스 프로세스와 비용 관리를 통해 수익성을 꾸준히 향상시키고 있습니다."
    };

    @Transactional
    public String save(final Stock stock) {
        return stockRepository
                .save(stock)
                .getSrtnCd();
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void setStockRepository() {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .readTimeout(30000, TimeUnit.MILLISECONDS) // 10 초 타임아웃
                .build();
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
                List<Stock> stocks = ParseStockJsonResponse.parseResponses(responseBody);
                stockRepository.saveAll(stocks);
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
            Stock stock = getFromDartByStrnCd(strnCd).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
            stockRepository.save(stock);
            return stock;
        }
        return byItmsNm.get();
    }

    @Transactional
    public Stock findByItmsNm(final String itmsNm) {
        Optional<Stock> byItmsNm = stockRepository.findStockByItmsNm(itmsNm);
        if (byItmsNm.isEmpty()) {
            Stock stock = getFromDartByItmsNm(itmsNm).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
            stockRepository.save(stock);
            return stock;
        }
        return byItmsNm.get();
    }

    private Optional<Stock> getFromDartByStrnCd(final String srtnCd) {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .readTimeout(30000, TimeUnit.MILLISECONDS) // 10 초 타임아웃
                .build();
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
                stock = ParseStockJsonResponse.parseSingleResponse(responseBody);
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
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .readTimeout(30000, TimeUnit.MILLISECONDS) // 10 초 타임아웃
                .build();
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
                stock = ParseStockJsonResponse.parseSingleResponse(responseBody);
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

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void setRecommendAggressiveStocks() {
        List<Stock> stocks = stockRepository.findAggressiveStocks().orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));

        for (Stock stock : stocks) {
            int random = RandomNumberGenerator.random(0, comments.length - 1);

            RecommendStock recommendStock = new RecommendStock(
                    stock.getSrtnCd()
                    , stock.getItmsNm()
                    , stock.getClpr()
                    , comments[random]
                    , stock.getVs()
                    , InvestorType.AGGRESSIVE);
            recommendStockRepository.save(recommendStock);
        }
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void setRecommendModerateStock() {
        List<Stock> stocks = stockRepository.findModerateStock().orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));

        for (Stock stock : stocks) {
            int random = RandomNumberGenerator.random(0, comments.length - 1);

            RecommendStock recommendStock = new RecommendStock(
                    stock.getSrtnCd()
                    , stock.getItmsNm()
                    , stock.getClpr()
                    , comments[random]
                    , stock.getVs()
                    , InvestorType.MODERATE);
            recommendStockRepository.save(recommendStock);
        }
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void setRecommendConservativeStock() {
        List<Stock> stocks = stockRepository.findConservativeStock().orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));

        for (Stock stock : stocks) {
            int random = RandomNumberGenerator.random(0, comments.length - 1);

            RecommendStock recommendStock = new RecommendStock(
                    stock.getSrtnCd()
                    , stock.getItmsNm()
                    , stock.getClpr()
                    , comments[random]
                    , stock.getVs()
                    , InvestorType.CONSERVATIVE);
            recommendStockRepository.save(recommendStock);
        }
    }

    @Transactional
    public Page<RecommendStockResponse> getRecommendedStocks(Pageable pageable) {
        Page<RecommendStock> page = recommendStockRepository.findAll(pageable);

        List<RecommendStockResponse> recommends = page
                .getContent()
                .stream()
                .map(RecommendStockResponse::new)
                .toList();

        return new PageImpl<>(recommends, pageable, page.getTotalElements());
    }

    @Transactional
    public String update(final String strnCd, final Stock req) {
        Stock stock = stockRepository.findById(strnCd).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
        stock.update(req.getClpr(), req.getMkp());
        return stock.getSrtnCd();
    }

    @Transactional
    public String delete(final String strnCd) {
        Stock stock = stockRepository.findById(strnCd).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
        stockRepository.delete(stock);
        return stock.getSrtnCd();
    }

    @Transactional
    public String deleteByItmsNm(final String itmsNm) {
        Stock stock = stockRepository.findStockByItmsNm(itmsNm).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
        stockRepository.delete(stock);
        return stock.getSrtnCd();
    }
}
