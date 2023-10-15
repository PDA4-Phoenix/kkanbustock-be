package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class SolvedStockQuizResponse {
    private final String content;
    private final String answer;
    private final String explanation;

    @Builder
    public SolvedStockQuizResponse(StockQuiz stockQuiz) {
        this.content = stockQuiz.getContent();
        this.answer = stockQuiz.getAnswer();
        this.explanation = stockQuiz.getExplanation();
    }
}
