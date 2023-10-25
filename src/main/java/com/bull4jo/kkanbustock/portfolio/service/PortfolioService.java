package com.bull4jo.kkanbustock.portfolio.service;

import com.bull4jo.kkanbustock.common.RandomNumberGenerator;
import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioPK;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;


    // 포트폴리오 단일 조회
    @Transactional(readOnly = true)
    public Portfolio findByMemberIdAndStockId(final PortfolioPK portfolioPK) {
        return portfolioRepository
                .findById(portfolioPK)
                .orElseThrow(() -> new CustomException(ErrorCode.PORTFOLIO_NOT_FOUND));
    }

    // 포트폴리오 전체조회 (멤버 id로)
    @Transactional(readOnly = true)
    public List<Portfolio> findByMemberId(final String memberId) {
        return portfolioRepository
                .findPortfoliosByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.PORTFOLIO_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public float getMemberProfitRate(final String memberId) {
        // 멤버가 없을 경우 예외처리 필요

        // 포폴 없을경우 예외처리 필요
        float equitiesSum = portfolioRepository
                .calculateTotalEquitiesValueByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_EQUITIES_VALUE_AMOUNT));

        float purchaseAmountSum = portfolioRepository
                .calculateTotalPurchaseAmountByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_TOTAL_PURCHASE_AMOUNT));

        float profitRate = equitiesSum / purchaseAmountSum;

        return profitRate;
    }

    @Transactional(readOnly = true)
    public float getTotalPurchaseAmountByMemberId(final String memberId) {
        return portfolioRepository
                .calculateTotalPurchaseAmountByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_TOTAL_PURCHASE_AMOUNT));
    }

    @Transactional(readOnly = true)
    public float calculateTotalEquitiesValueByMemberId(final String memberId) {
        return portfolioRepository
                .calculateTotalEquitiesValueByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_EQUITIES_VALUE_AMOUNT));
    }

    @Transactional
    public String addPortfolio(final String memberId, final String stockId, final float purchasePrice, final int quantity) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Stock stock = stockRepository
                .findById(stockId)
                .orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_COUND));

        Portfolio portfolio = Portfolio.builder()
                .portfolioPK(
                        PortfolioPK.builder()
                                .memberId(memberId)
                                .stockId(stockId)
                                .build()
                )
                .member(member)
                .stock(stock)
                .purchasePrice(purchasePrice)
                .quantity(quantity)
                .build();

        portfolio.setDerivedAttributes();

        System.out.println(portfolio);
        return portfolioRepository
                .save(portfolio)
                .getPortfolioPK()
                .getMemberId();
    }

    @Scheduled(cron = "0 1 2 * * *", zone = "Asia/Seoul")
    public void updatePortfolio() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        for (Portfolio portfolio : portfolios) {
            portfolio.setDerivedAttributes();
        }

        // 메서드 실행 시작 로그
        System.out.println("updatePortfolio() 메서드가 실행됩니다.");
    }

//    @Transactional
    public String setRandomPortfolio(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Stock> stocks = stockRepository.findRandomStocks().orElseThrow(() -> new RuntimeException("stock 랜덤 못빼옴"));
        int stockSize = stocks.size();
        int count = RandomNumberGenerator.random(2, 8);

        for (int i = 0; i < count; i++) {
            PortfolioPK portfolioPK = new PortfolioPK(memberId, stocks.get(i).getSrtnCd());
            double purchasePrice = RandomNumberGenerator.random(stocks.get(i).getClpr() * 0.7, (float) stocks.get(i).getClpr() * 1.3);

            int quantity = RandomNumberGenerator.random(1, 50);
            Portfolio portfolio = new Portfolio(portfolioPK, member, stocks.get(i), (float) purchasePrice, quantity);

            portfolioRepository.save(portfolio);
        }

        return memberId;
    }
}
