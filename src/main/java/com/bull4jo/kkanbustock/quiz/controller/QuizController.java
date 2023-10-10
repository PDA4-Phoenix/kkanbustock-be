package com.bull4jo.kkanbustock.quiz.controller;

import com.bull4jo.kkanbustock.common.config.Response;
import com.bull4jo.kkanbustock.quiz.domain.request.QuizRequest;
import com.bull4jo.kkanbustock.quiz.domain.response.QuizResponse;
import com.bull4jo.kkanbustock.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/v1/quizzes")
    public Response<List<QuizResponse>> getQuizzes() {
        return Response.of(quizService.getQuizzes());
    }

    @GetMapping("/v1/quizzes/{quizId}")
    public Response<QuizResponse> getQuiz(
            @PathVariable(value = "quizId") long id
    ) {
        return Response.of(quizService.getQuiz(id));
    }

    @PostMapping("/v1/quizzes")
    public void saveSolvedQuiz(@RequestBody QuizRequest quizRequest) {
        quizService.saveSolvedQuiz(quizRequest);
    }
}
