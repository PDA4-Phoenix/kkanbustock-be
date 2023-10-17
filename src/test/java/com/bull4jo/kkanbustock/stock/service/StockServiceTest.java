package com.bull4jo.kkanbustock.stock.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    StockService stockService;
    @Test
    void findByStrnCd() {
        long startTime = System.nanoTime();

        // 코드 실행
//        stockService.getFromDartByStrnCd("000075");
        // 코드 실행 후 시간 측정
        long endTime = System.nanoTime();

        // 실행 시간 계산 (나노초 단위)
        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        stockService.findByStrnCd("000075");
        endTime = System.nanoTime();

        duration = endTime - startTime;
        System.out.println("duration = " + duration);
    }
    @Test
    public void findSinhan() throws Exception {
        // given
        stockService.getFromShinhan();
        // when

        // then
    }
}