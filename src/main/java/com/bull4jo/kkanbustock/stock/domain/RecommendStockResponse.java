package com.bull4jo.kkanbustock.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendStockResponse {
    /**
     * 종목의 명칭
     * */
    private String itmsNm;

    /**
     * 정규시장의 매매시간 종료시까지 형성되는 최종가격
     */
    private int clpr;

    /**
     * 종목 개요
     */
    private String content;

    /**
     * 전일대비 등락
     */
    private int vs;

    public RecommendStockResponse(RecommendStock recommendStock) {
        this.itmsNm = recommendStock.getItmsNm();
        this.clpr = recommendStock.getClpr();
        this.content = recommendStock.getContent();
        this.vs = recommendStock.getVs();
    }
}
