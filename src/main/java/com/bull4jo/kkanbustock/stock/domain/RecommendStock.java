package com.bull4jo.kkanbustock.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendStock {
    /**
     * 종목 코드
     */
    @Id
    private String srtnCd;

    /**
     * 종목의 명칭
     * */
    @Column
    private String itmsNm;

    /**
     * 정규시장의 매매시간 종료시까지 형성되는 최종가격
     */
    @Column
    private int clpr;

    /**
     * 종목 개요
     */
    @Column
    private String content;

    /**
     * 전일대비 등락
     */
    @Column
    private int vs;


}
