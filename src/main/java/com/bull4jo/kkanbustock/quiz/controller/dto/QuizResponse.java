package com.bull4jo.kkanbustock.quiz.controller.dto;

import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizResponse {
    private final Long id;
    private final String content;
    private final String answer;
    private final String explanation;

    public static QuizResponse of(StockQuiz entity) {
        return new QuizResponse(
                entity.getId(),
                entity.getContent(),
                entity.getAnswer(),
                entity.getExplanation()
        );
    }
}
