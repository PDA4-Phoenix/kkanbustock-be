package com.bull4jo.kkanbustock.riskprofilequestion.controller;

import com.bull4jo.kkanbustock.riskprofilequestion.controller.dto.RiskProfileQuestionResponse;
import com.bull4jo.kkanbustock.riskprofilequestion.domain.InvestorType;
import com.bull4jo.kkanbustock.riskprofilequestion.service.RiskProfileQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RiskProfileQuestionController {
    private final RiskProfileQuestionService riskProfileQuestionService;

    @GetMapping("/v1/risk-profile-questions")
    public ResponseEntity<List<RiskProfileQuestionResponse>> getRiskProfileQuestion() {
        return ResponseEntity
                .ok(riskProfileQuestionService.findAll());
    }

    @PostMapping("/v1/risk-profile-questions")
    public ResponseEntity<InvestorType> analyzeQuestionResults(@RequestBody boolean[] surveyResponses) {
        return ResponseEntity
                .ok(riskProfileQuestionService.analyzeQuestionResults(surveyResponses));
    }
}
