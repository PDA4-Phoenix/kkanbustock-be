package com.bull4jo.kkanbustock.stock.controller;

import com.bull4jo.kkanbustock.member.domain.entity.InvestorType;
import com.bull4jo.kkanbustock.stock.domain.RecommendStockResponse;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://team-4.shinhansec-pda.net/")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/v1/stocks/")
    public ResponseEntity<Stock> searchStockById(@RequestParam(value = "srtnCd") final String srtnCd) {
        return ResponseEntity.ok(stockService.findByStrnCd(srtnCd));
    }

    @GetMapping("/v1/stocks")
    public ResponseEntity<Stock> searchStockByName(@RequestParam(value = "itmsNm") final String itmsNm) {
        return ResponseEntity.ok(stockService.findByItmsNm(itmsNm));
    }

    @GetMapping("/v1/recommends")
    public ResponseEntity<Page<RecommendStockResponse>> getRecommendedStocks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(stockService.getRecommendedStocks(pageable));
    }

    @GetMapping("/v1/recommends/{investorType}")
    public ResponseEntity<Page<RecommendStockResponse>> getRecommendStocksByInvestorType(
            @PathVariable InvestorType investorType,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(stockService.getRecommendStocksByInvestorType(investorType, pageable));
    }

    @GetMapping("/v1/init-stocks")
    public void setInitStock() {
        stockService.setStockRepository();
    }

    @GetMapping("/v1/init-recommend-stocks")
    public void setInitRecommendStock() {
        stockService.setRecommendModerateStock();
        stockService.setRecommendConservativeStock();
        stockService.setRecommendAggressiveStocks();
    }
}
