package com.bull4jo.kkanbustock.stock.repository;

import com.bull4jo.kkanbustock.member.domain.entity.InvestorType;
import com.bull4jo.kkanbustock.stock.domain.RecommendStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendStockRepository extends JpaRepository<RecommendStock, String> {
    @Override
    Page<RecommendStock> findAll(Pageable pageable);

    Optional<Page<RecommendStock>> findByInvestorType(InvestorType investorType, Pageable pageable);
}
