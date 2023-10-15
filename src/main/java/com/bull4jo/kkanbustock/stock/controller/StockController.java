package com.bull4jo.kkanbustock.stock.controller;

import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public void test() {
        //"005930"
        String samsung = "005930";
//        stockService.findById("005930");
//        stockService.findByItmsNm("삼성전자");
        stockService.setInit();
    }
}
