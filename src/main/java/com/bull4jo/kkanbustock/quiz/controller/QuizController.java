package com.bull4jo.kkanbustock.quiz.controller;

import com.bull4jo.kkanbustock.quiz.controller.dto.DailyQuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.QuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.QuizResponse;
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
    public ResponseEntity<QuizResponse> getDailyQuiz(
            @RequestBody DailyQuizRequest dailyQuizRequest
    ) {
        return ResponseEntity.ok(quizService.getDailyQuiz(dailyQuizRequest));
    }

    @GetMapping("/v1/quizzes")
    public ResponseEntity<List<QuizResponse>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @PostMapping("/v1/quizzes")
    public void saveSolvedQuiz(@RequestBody QuizRequest quizRequest) {
        quizService.saveSolvedQuiz(quizRequest);
    }
}
