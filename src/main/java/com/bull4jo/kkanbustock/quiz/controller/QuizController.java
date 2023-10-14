package com.bull4jo.kkanbustock.quiz.controller;

import com.bull4jo.kkanbustock.quiz.controller.dto.QuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedQuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.DailyQuizResponse;
import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedStockQuizResponse;
import com.bull4jo.kkanbustock.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/v1/quizzes/daily")
    public ResponseEntity<DailyQuizResponse> getDailyQuiz(
            @RequestBody QuizRequest quizRequest
    ) {
        return ResponseEntity.ok(quizService.getDailyQuiz(quizRequest));
    }

    @GetMapping("/v1/quizzes")
    public ResponseEntity<SolvedStockQuizResponse> getSolvedQuizzes(
            @RequestBody QuizRequest quizRequest
    ) {
        return ResponseEntity.ok(quizService.getSolvedQuizzes(quizRequest));
    }

    @PostMapping("/v1/quizzes/daily")
    public void saveSolvedQuiz(@RequestBody SolvedQuizRequest solvedQuizRequest) {
        quizService.saveSolvedQuiz(solvedQuizRequest);
    }
}
