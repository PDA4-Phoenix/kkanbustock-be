package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SolvedStockQuizResponse {
    private final String content;
    private final String answer;
    private final String explanation;
    private final LocalDateTime solvedDate;
    private final boolean isCorrect;

    @Builder
    public SolvedStockQuizResponse(StockQuiz stockQuiz, SolvedStockQuiz solvedStockQuiz) {
        this.content = stockQuiz.getContent();
        this.answer = stockQuiz.getAnswer();
        this.explanation = stockQuiz.getExplanation();
        this.solvedDate = solvedStockQuiz.getSolvedDate();
        this.isCorrect = solvedStockQuiz.getIsCorrect();
    }
}
