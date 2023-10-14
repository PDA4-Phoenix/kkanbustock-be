package com.bull4jo.kkanbustock.quiz.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SolvedQuizRequest {
    private final String memberId;
    private final Long stockQuizId;
    private final Boolean isCorrect;
}
