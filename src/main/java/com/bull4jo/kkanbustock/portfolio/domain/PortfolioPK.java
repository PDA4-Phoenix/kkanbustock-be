package com.bull4jo.kkanbustock.portfolio.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PortfolioPK implements Serializable {
    private Long memberId;
    private String stockId;
}
