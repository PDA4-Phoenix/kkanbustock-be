package com.bull4jo.kkanbustock.portfolio.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioPK;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import com.bull4jo.kkanbustock.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Portfolio> portfolios = portfolioRepository.findPortfoliosByMemberId(memberId);

        float equitiesSum = 0;
        float purchaseAmountSum = 0;
        for (Portfolio portfolio : portfolios) {
            equitiesSum += portfolio.getEquitiesValue();
            purchaseAmountSum += portfolio.getPurchaseAmount();
        }

        float profitRate = equitiesSum / purchaseAmountSum;
        return profitRate;
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
}
