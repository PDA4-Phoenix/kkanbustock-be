package com.bull4jo.kkanbustock.riskprofilequestion.service;

import com.bull4jo.kkanbustock.riskprofilequestion.controller.dto.RiskProfileQuestionResponse;
import com.bull4jo.kkanbustock.riskprofilequestion.domain.InvestorType;
import com.bull4jo.kkanbustock.riskprofilequestion.repository.RiskProfileQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskProfileQuestionService {
    private final RiskProfileQuestionRepository riskProfileQuestionRepository;

    public List<RiskProfileQuestionResponse> findAll() {
        return riskProfileQuestionRepository
                .findAll()
                .stream()
                .map(RiskProfileQuestionResponse::new)
                .collect(Collectors.toList());
    }

    public InvestorType analyzeQuestionResults(int[] surveyResponses) {
        int sum = Arrays.stream(surveyResponses).sum();
        if (sum >= 0 && sum <= 4) {
            return InvestorType.CONSERVATIVE;
        } else if (sum >= 5 && sum <= 7) {
            return InvestorType.MODERATE;
        } else if (sum >= 8 && sum <= 10) {
            return InvestorType.AGGRESSIVE;
        } else {
            throw new RuntimeException();
        }
    }
}
