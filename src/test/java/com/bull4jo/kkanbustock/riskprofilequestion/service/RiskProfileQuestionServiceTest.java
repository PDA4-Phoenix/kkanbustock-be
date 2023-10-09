package com.bull4jo.kkanbustock.riskprofilequestion.service;

import com.bull4jo.kkanbustock.riskprofilequestion.controller.dto.RiskProfileQuestionResponse;
import com.bull4jo.kkanbustock.riskprofilequestion.domain.InvestorType;
import com.bull4jo.kkanbustock.riskprofilequestion.domain.RiskProfileQuestion;
import com.bull4jo.kkanbustock.riskprofilequestion.repository.RiskProfileQuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RiskProfileQuestionServiceTest {
    @Autowired
    RiskProfileQuestionService riskProfileQuestionService;
    @Autowired
    RiskProfileQuestionRepository riskProfileQuestionRepository;

    @Test
    public void 투자자_성향_설문_전체조회_테스트() throws Exception {
        // given
        RiskProfileQuestion riskProfileQuestion = new RiskProfileQuestion(
                1L,
                "기대없이 넣어둔 주식이 갑자기 쭉쭉 오르고 있다!", "아싸 돈벌었다~ 목표 수익을 달성해서 돈을 뺀다.",
                "더 오를 것 같은데..? 감을 믿고 떡상을 기대한다."
        );
        riskProfileQuestionRepository.save(riskProfileQuestion);
        // when
        List<RiskProfileQuestionResponse> all = riskProfileQuestionService.findAll();
        // then
        Assertions.assertNotNull(all);
    }

    @Test
    public void 투자자_성향_응답_분류_테스트() throws Exception {
        // given
        boolean[] ans = {true, true, true, true, true, false, false, false, false, false};
        // when
        InvestorType investorType = riskProfileQuestionService.analyzeQuestionResults(ans);
        // then
        Assertions.assertEquals(InvestorType.MODERATE, investorType);
    }
}