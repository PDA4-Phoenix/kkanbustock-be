package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SolvedDailyQuizResponse {
    private final Boolean isCorrect;
    
    @Builder
    public SolvedDailyQuizResponse(SolvedStockQuiz solvedStockQuiz) {
        this.isCorrect = solvedStockQuiz.getIsCorrect();
    }
}
