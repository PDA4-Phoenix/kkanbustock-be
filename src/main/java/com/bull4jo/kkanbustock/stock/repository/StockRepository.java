package com.bull4jo.kkanbustock.stock.repository;

import com.bull4jo.kkanbustock.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findStockByItmsNm(String itmsNum);

    @Query(value = "SELECT * FROM stock order by RAND() LIMIT 20",nativeQuery = true)
    Optional<List<Stock>> findRandomStocks();
}
