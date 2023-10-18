package com.bull4jo.kkanbustock.quiz.controller;

import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedQuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.DailyQuizResponse;
import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedStockQuizResponse;
import com.bull4jo.kkanbustock.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
    public ResponseEntity<List<SolvedStockQuizResponse>> getSolvedQuizzes(
            @PathVariable String memberId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(quizService.getSolvedQuizzes(memberId, pageable));
    }

    @PostMapping("/v1/quizzes/daily")
    public Long saveSolvedQuiz(@RequestBody SolvedQuizRequest solvedQuizRequest) {
        return quizService.saveSolvedQuiz(solvedQuizRequest);
    }
}
