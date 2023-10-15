package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DailyQuizResponse {
    private final Long id;
    private final String content;
    private final String answer;
    private final String explanation;
    private final boolean isSolved;

    @Builder
    public DailyQuizResponse(StockQuiz stockQuiz, boolean isSolved) {
        this.id = stockQuiz.getId();
        this.content = stockQuiz.getContent();
        this.answer = stockQuiz.getAnswer();
        this.explanation = stockQuiz.getExplanation();
        this.isSolved = isSolved;
    }
}