package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizResponse {
    private final Long id;
    private final String content;
    private final String answer;
    private final String explanation;

    @Builder
    public QuizResponse(StockQuiz stockQuiz) {
        this.id = stockQuiz.getId();
        this.content = stockQuiz.getContent();
        this.answer = stockQuiz.getAnswer();
        this.explanation = stockQuiz.getExplanation();
    }
}
