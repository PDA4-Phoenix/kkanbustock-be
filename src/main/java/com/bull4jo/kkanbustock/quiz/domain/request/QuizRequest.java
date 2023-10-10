package com.bull4jo.kkanbustock.quiz.domain.request;

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
