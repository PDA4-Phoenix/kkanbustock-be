package com.bull4jo.kkanbustock.quiz.controller;

import com.bull4jo.kkanbustock.quiz.domain.dto.QuizRequest;
import com.bull4jo.kkanbustock.quiz.domain.dto.QuizResponse;
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

    @GetMapping("/v1/quizzes")
    public ResponseEntity<List<QuizResponse>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @GetMapping("/v1/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> getQuiz(
            @PathVariable(value = "quizId") long id
    ) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @PostMapping("/v1/quizzes")
    public void saveSolvedQuiz(@RequestBody QuizRequest quizRequest) {
        quizService.saveSolvedQuiz(quizRequest);
    }
}
