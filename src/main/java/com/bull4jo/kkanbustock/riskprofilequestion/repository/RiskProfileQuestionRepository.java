package com.bull4jo.kkanbustock.riskprofilequestion.repository;

import com.bull4jo.kkanbustock.riskprofilequestion.domain.RiskProfileQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskProfileQuestionRepository extends JpaRepository<RiskProfileQuestion, Long> {
}
