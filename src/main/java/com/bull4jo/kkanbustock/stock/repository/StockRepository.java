package com.bull4jo.kkanbustock.stock.repository;

import com.bull4jo.kkanbustock.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findStockByItmsNm(String itmsNum);
}
