package com.bull4jo.kkanbustock.portfolio.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioPK implements Serializable {
    private String memberId;
    private String stockId;
}
