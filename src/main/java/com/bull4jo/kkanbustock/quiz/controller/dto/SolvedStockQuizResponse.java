package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
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
