package com.bull4jo.kkanbustock.quiz.controller;

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

    @GetMapping("/v1/quizzes/daily/{memberId}")
    public ResponseEntity<DailyQuizResponse> getDailyQuiz(
            @PathVariable String memberId
    ) {
        return ResponseEntity.ok(quizService.getDailyQuiz(memberId));
    }

    @GetMapping("/v1/quizzes/{memberId}")
    public ResponseEntity<SolvedStockQuizResponse> getSolvedQuizzes(
            @PathVariable String memberId
    ) {
        return ResponseEntity.ok(quizService.getSolvedQuizzes(memberId));
    }

    @PostMapping("/v1/quizzes/daily")
    public void saveSolvedQuiz(@RequestBody SolvedQuizRequest solvedQuizRequest) {
        quizService.saveSolvedQuiz(solvedQuizRequest);
    }
}
