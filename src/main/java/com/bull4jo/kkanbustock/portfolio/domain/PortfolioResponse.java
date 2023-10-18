package com.bull4jo.kkanbustock.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class PortfolioResponse {
    private String memberId;
    private String stockId;

    // 종목명
    private String itmsNm;

    // 개별 종목 매수가
    private float purchasePrice;

    // 개별 종목 매수 수량
    private int quantity;

    /**
     * 파생 속성
     */
    // 개별 종목 매수 금액 = 매수가 * 매수 수량
    private float purchaseAmount;

    // 개별 종목 평가 금액 = 매수 수량 * 종가
    private int equitiesValue;

    // 개별 종목 수익률 = 평가 금액 / 총 매수 금액
    private float profitRate;

    // 개별 종목 평가 손익 = 평가 금액 - 총 매수 금액
    private float gainsLosses;

    public PortfolioResponse(Portfolio portfolio) {
        this.memberId = portfolio.getPortfolioPK().getMemberId();
        this.stockId = portfolio.getPortfolioPK().getStockId();
        this.itmsNm = portfolio.getStock().getItmsNm();
        this.purchasePrice = portfolio.getPurchasePrice();
        this.quantity = portfolio.getQuantity();
        this.purchaseAmount = portfolio.getPurchaseAmount();
        this.equitiesValue = portfolio.getEquitiesValue();
        this.profitRate = portfolio.getProfitRate();
        this.gainsLosses = portfolio.getGainsLosses();
    }
}
