package com.bull4jo.kkanbustock.riskprofilequestion.controller;

import com.bull4jo.kkanbustock.riskprofilequestion.controller.dto.RiskProfileQuestionResponse;
import com.bull4jo.kkanbustock.riskprofilequestion.domain.InvestorType;
import com.bull4jo.kkanbustock.riskprofilequestion.service.RiskProfileQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RiskProfileQuestionController {
    private final RiskProfileQuestionService riskProfileQuestionService;

    @GetMapping("/v1/risk-profile-questions")
    public List<RiskProfileQuestionResponse> getRiskProfileQuestion() {
        return riskProfileQuestionService.findAll();
    }

    @PostMapping("/v1/risk-profile-questions")
    public InvestorType analyzeQuestionResults(@RequestBody int[] surveyResponses) {
        return riskProfileQuestionService.analyzeQuestionResults(surveyResponses);
    }
}
