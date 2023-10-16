package com.bull4jo.kkanbustock.portfolio.service;

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
        return portfolioRepository.findById(portfolioPK).orElseThrow();
    }

    // 포트폴리오 전체조회 (멤버 id로)
    @Transactional(readOnly = true)
    public List<Portfolio> findByMemberId(final String memberId) {
        return portfolioRepository.findPortfoliosByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public float getMemberProfitRate(final String memberId) {
        // 멤버가 없을 경우 예외처리 필요

        // 포폴 없을경우 예외처리 필요
        float equitiesSum = portfolioRepository.calculateTotalEquitiesValueByMemberId(memberId);
        float purchaseAmountSum = portfolioRepository.calculateTotalPurchaseAmountByMemberId(memberId);
        float profitRate = equitiesSum / purchaseAmountSum;

        return profitRate;
    }

    @Transactional(readOnly = true)
    public float getTotalPurchaseAmountByMemberId(final String memberId) {
        return portfolioRepository.calculateTotalPurchaseAmountByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public float calculateTotalEquitiesValueByMemberId(final String memberId) {
        return portfolioRepository.calculateTotalEquitiesValueByMemberId(memberId);
    }

    @Transactional
    public String addPortfolio(final String memberId, final String stockId, final float purchasePrice, final int quantity) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Stock stock = stockRepository.findById(stockId).orElseThrow();

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
        return portfolioRepository.save(portfolio).getPortfolioPK().getMemberId();
    }


    /**
     * 미완;
     * @Query("SELECT p FROM Portfolio p ORDER BY RAND()")
     * stockRepository에서 랜덤 n개 엔티티 가져오는 로직 개선필요..
     */
    @Transactional
    public void generateRandomPortfolio(final String memberId) {
        Random random = new Random();
        Member member = memberRepository.findById(memberId).orElseThrow();
        int min = 2;
        int max = 5;
        int rdm = random.nextInt(max - min + 1) - min;
        List<Stock> nRandomStock = getNRandomStock(rdm);
        for (Stock stock: nRandomStock) {
            Portfolio portfolio = Portfolio
                    .builder()
                    .member(member)
                    .stock(stock)
                    .purchasePrice(3)
                    .quantity(3)
                    .build();
            portfolio.setDerivedAttributes();
            portfolioRepository.save(portfolio);
        }

    }

    @Scheduled(cron = "0 1 2 * * *")
    public void updatePortfolio() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        for (Portfolio portfolio : portfolios) {
            portfolio.setDerivedAttributes();
        }

        // 메서드 실행 시작 로그
        System.out.println("updatePortfolio() 메서드가 실행됩니다.");
    }

    private List<Stock> getNRandomStock(int n) {
        return new ArrayList<>();
    }
}
