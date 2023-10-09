package com.bull4jo.kkanbustock.riskprofilequestion.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RiskProfileQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String content;
    @Column(length = 100, nullable = false)
    private String option1;
    @Column(length = 100, nullable = false)
    private String option2;

}
