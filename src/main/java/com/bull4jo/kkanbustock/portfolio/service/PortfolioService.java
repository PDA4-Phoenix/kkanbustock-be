package com.bull4jo.kkanbustock.portfolio.service;

import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioPK;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    // 포트폴리오 단일 조회
    @Transactional(readOnly = true)
    public Portfolio findByMemberIdAndStockId(final PortfolioPK portfolioPK) {
        return portfolioRepository.findById(portfolioPK).orElseThrow();
    }

    // 포트폴리오 전체조회 (멤버 id로)
    @Transactional(readOnly = true)
    public List<Portfolio> findByMemberId(final Long memberId) {
        List<Portfolio> portfoliosByMemberId = portfolioRepository.findPortfoliosByMemberId(memberId);
        for (Portfolio portfolio : portfoliosByMemberId) {
            System.out.println(portfolio.toString());
        }
        return portfoliosByMemberId;
    }

    @Transactional(readOnly = true)
    public float getMemberProfitRate(final Long memberId) {
        // 멤버가 없을 경우 예외처리 필요

        // 포폴 없을경우 예외처리 필요
        List<Portfolio> portfolios = portfolioRepository.findPortfoliosByMemberId(memberId);

        float portfolioDenom = 0;
        float investedAsset = 0;
        for (Portfolio portfolio : portfolios) {
            float purchasePrice = portfolio.getPurchasePrice();
            int stockQuantity = portfolio.getQuantity();
            float stockProfitRate = portfolio.getProfitRate();

            investedAsset += purchasePrice * stockQuantity;
            portfolioDenom += stockProfitRate * purchasePrice * stockQuantity;
        }

        float profitRate = portfolioDenom / investedAsset;
        return profitRate;
    }
}
