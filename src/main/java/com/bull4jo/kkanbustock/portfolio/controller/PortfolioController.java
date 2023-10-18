package com.bull4jo.kkanbustock.portfolio.controller;

import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioPK;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioResponse;
import com.bull4jo.kkanbustock.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/v1/portfolios")
    public PortfolioResponse findByMemberIdAndStockId(@RequestParam PortfolioPK portfolioPK) {
        return portfolioService.findByMemberIdAndStockId(portfolioPK);
    }

    @GetMapping("/v1/portfolios/{memberId}")
    public List<PortfolioResponse> findByMemberId(@PathVariable String memberId) {
        return portfolioService.findByMemberId(memberId);
    }

    @GetMapping("/v1/portfolios/profits/{memberId}")
    public float getMemberProfitRate(@PathVariable String memberId) {
        return portfolioService.getMemberProfitRate(memberId);
    }

}
