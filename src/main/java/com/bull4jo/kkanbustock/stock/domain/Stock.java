package com.bull4jo.kkanbustock.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    /**
     * 종목 코드보다 짧으면서 유일성이 보장되는 코드(6자리)
     * */
    @Id
    private String srtnCd;

    /**
     * 종목의 명칭
     * */
    @Column
    private String itmsNm;

    /**
     * 주식의 시장 구분 (KOSPI/KOSDAQ/KONEX 중 1)
     * */
    @Column
    private MarketType mrktCtg;

    /**
     * 정규시장의 매매시간 종료시까지 형성되는 최종가격
     */
    @Column
    private int clpr;

    /**
     * 정규시장의 매매시간
     * 개시 후 형성되는 최초가격
     */
    @Column
    private int mkp;

    /**
     * 기준 일자
     */
    @Column
    private int basDt;

    /**
     * 전일 대비 등락
     */
    @Column
    private int vs;

    /**
     * 전일 대비 등락에 따른 비율
     */
    @Column
    private float fltRt;

    /**
     * 거래대금
     * 거래건 별 체결가격 * 체결수량의 누적 합계
     */
    @Column
    private long trPrc;


    /**
     * 시가총액
     */
    @Column
    private long mrktTotAmt;

    public void update(int clpr, int mkp) {
        this.clpr = clpr;
        this.mkp = mkp;
    }
}
