package com.bull4jo.kkanbustock.stock.repository;

import com.bull4jo.kkanbustock.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findStockByItmsNm(String itmsNum);

    @Query(value = "SELECT * FROM stock ORDER BY RAND() LIMIT 20", nativeQuery = true)
    Optional<List<Stock>> findRandomStocks();

    @Query(value = "SELECT * FROM stock ORDER BY mrkt_tot_amt DESC LIMIT 500", nativeQuery = true)
    Optional<List<Stock>> findTopByMrktTotAmt();

    @Query("SELECT s FROM Stock s WHERE (s.fltRt >= 10 AND s.trPrc >= 50000000000)")
    Optional<List<Stock>> findAggressiveStocks();

    @Query("SELECT s FROM Stock s WHERE (s.fltRt <= 5 AND s.fltRt >= -5 AND s.mrktTotAmt >= 50000000000)")
    Optional<List<Stock>> findModerateStock();

    @Query("SELECT s FROM Stock s WHERE (s.fltRt <= 3 AND s.fltRt >= -3 AND s.mrktTotAmt >= 1000000000000)")
    Optional<List<Stock>> findConservativeStock();
}
