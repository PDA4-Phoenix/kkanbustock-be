package com.bull4jo.kkanbustock.portfolio.repository;

import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioPK> {
    List<Portfolio> findPortfoliosByMemberId(Long memberId);
}
