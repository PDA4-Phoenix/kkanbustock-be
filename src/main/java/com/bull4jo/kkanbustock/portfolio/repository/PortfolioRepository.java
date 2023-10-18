package com.bull4jo.kkanbustock.portfolio.repository;

import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioPK> {
    Optional<List<Portfolio>> findByMemberId(String memberId);

    // 멤버 계좌 전체 매수 금액 계산
    @Query("SELECT SUM(p.purchaseAmount) FROM Portfolio p WHERE p.member.id = :memberId")
    Optional<Float> calculateTotalPurchaseAmountByMemberId(@Param("memberId") String memberId);

    // 멤버 계좌 전체 평가 금액 계산
    @Query("SELECT SUM(p.equitiesValue) FROM Portfolio  p WHERE p.member.id = :memberId")
    Optional<Float> calculateTotalEquitiesValueByMemberId(@Param("memberId") String memberId);

}
