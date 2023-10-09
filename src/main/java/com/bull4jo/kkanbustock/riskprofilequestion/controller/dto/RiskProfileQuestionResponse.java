package com.bull4jo.kkanbustock.riskprofilequestion.controller.dto;

import com.bull4jo.kkanbustock.riskprofilequestion.domain.RiskProfileQuestion;
import lombok.Builder;

public class RiskProfileQuestionResponse {
    private final String content;
    private final int weight;

    @Builder
    public RiskProfileQuestionResponse(RiskProfileQuestion riskProfileQuestion) {
        this.content = riskProfileQuestion.getContent();
        this.weight = riskProfileQuestion.getWeight();
    }
}
