package com.bull4jo.kkanbustock.portfolio.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@IdClass(PortfolioPK.class)
public class Portfolio {
    @Id
    private Long memberId;
    @Id
    private String stockId; // srtnCd
    @Column
    private float profitRate;
    @Column
    private float purchasePrice;
    @Column
    private int quantity;

}
