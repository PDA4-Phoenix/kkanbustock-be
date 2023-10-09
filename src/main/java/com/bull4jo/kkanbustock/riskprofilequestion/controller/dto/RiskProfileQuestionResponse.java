package com.bull4jo.kkanbustock.riskprofilequestion.controller.dto;

import com.bull4jo.kkanbustock.riskprofilequestion.domain.RiskProfileQuestion;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RiskProfileQuestionResponse {
    private final String content;
    private final String option1;
    private final String option2;

    @Builder
    public RiskProfileQuestionResponse(RiskProfileQuestion riskProfileQuestion) {
        this.content = riskProfileQuestion.getContent();
        this.option1 = riskProfileQuestion.getOption1();
        this.option2 = riskProfileQuestion.getOption2();
    }
}
