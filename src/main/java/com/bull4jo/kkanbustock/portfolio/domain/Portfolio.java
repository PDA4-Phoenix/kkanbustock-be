package com.bull4jo.kkanbustock.portfolio.domain;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    @EmbeddedId
    private PortfolioPK portfolioPK;

    @ManyToOne
    @MapsId("memberId") // db -> member_id
    private Member member;

    @ManyToOne
    @MapsId("stockId") // db -> stock_srtn_cd
    private Stock stock;

    // 개별 종목 매수가
    @Column
    private float purchasePrice;

    // 개별 종목 매수 수량
    @Column
    private int quantity;

    /**
     * 파생 속성
     */
    // 개별 종목 매수 금액 = 매수가 * 매수 수량
    @Column
    private float purchaseAmount;

    // 개별 종목 평가 금액 = 매수 수량 * 종가
    @Column
    private int equitiesValue;

    // 개별 종목 수익률 = 평가 금액 / 총 매수 금액
    @Column
    private float profitRate;

    @Column
    // 개별 종목 평가 손익 = 평가 금액 - 총 매수 금액
    private float gainsLosses;

    // 개별 종목 파생 속성 연산
    public void setDerivedAttributes() {
        this.purchaseAmount = purchasePrice * quantity;
        this.equitiesValue = quantity * stock.getClpr();
        this.profitRate = (equitiesValue / purchaseAmount - 1) * 100; // 예외처리 필요 0
        this.gainsLosses = equitiesValue - purchaseAmount;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioPK=" + portfolioPK +
                ", member=" + member +
                ", stock=" + stock +
                ", purchasePrice=" + purchasePrice +
                ", quantity=" + quantity +
                ", purchaseAmount=" + purchaseAmount +
                ", equitiesValue=" + equitiesValue +
                ", profitRate=" + profitRate +
                ", gainsLosses=" + gainsLosses +
                '}';
    }
}
