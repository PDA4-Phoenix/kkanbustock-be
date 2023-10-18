package com.bull4jo.kkanbustock.stock.controller;

import com.bull4jo.kkanbustock.stock.domain.RecommendStockResponse;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/v1/stocks/")
    public Stock searchStockById(@RequestParam(value = "srtnCd") final String srtnCd) {
        return stockService.findByStrnCd(srtnCd);
    }

    @GetMapping("/v1/stocks")
    public Stock searchStockByName(@RequestParam(value = "itmsNm") final String itmsNm) {
        return stockService.findByItmsNm(itmsNm);
    }

    @GetMapping("/v1/recommends")
    public Page<RecommendStockResponse> getRecommendedStocks(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return stockService.getRecommendedStocks(pageable);
    }
    @GetMapping("/v1/init-stocks")
    public void setInitStock() {
        stockService.setStockRepository();
    }

    @GetMapping("/v1/init-recommend-stocks")
    public void setInitRecommendStock() {
        stockService.setRecommendStockRepository();
    }
}
