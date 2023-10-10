package com.bull4jo.kkanbustock.quiz.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class QuizRequest {
    private final Long memberId;
    private final Long stockQuizId;
    private final Date solveDate;
    private final Boolean isCorrect;
}
