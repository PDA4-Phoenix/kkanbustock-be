package com.bull4jo.kkanbustock.quiz.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizRequest {
    private final Long memberId;
}
