package com.bull4jo.kkanbustock.stock.repository;

import com.bull4jo.kkanbustock.stock.domain.RecommendStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendStockRepository extends JpaRepository<RecommendStock, String> {
    Page<RecommendStock> findAll(Pageable pageable);
}
