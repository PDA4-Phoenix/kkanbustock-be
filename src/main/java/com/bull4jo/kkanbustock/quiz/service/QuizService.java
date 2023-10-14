package com.bull4jo.kkanbustock.quiz.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.quiz.controller.dto.DailyQuizRequest;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import com.bull4jo.kkanbustock.quiz.controller.dto.QuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.QuizResponse;
import com.bull4jo.kkanbustock.quiz.repository.QuizRepository;
import com.bull4jo.kkanbustock.quiz.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public QuizResponse getDailyQuiz(DailyQuizRequest dailyQuizRequest) {
        Long memberId = dailyQuizRequest.getMemberId();

        List<SolvedStockQuiz> solvedQuizzes = solvedQuizRepository.findByMemberId(memberId);
        List<StockQuiz> quizzes = quizRepository.findAll();
        List<Long> unsolvedQuizIds = new ArrayList<>();

        for (StockQuiz quiz : quizzes) {
            Long quizId = quiz.getId();
            boolean isSolved = solvedQuizzes.stream().anyMatch(solvedQuiz -> solvedQuiz.getStockQuiz().getId().equals(quizId));
            if (!isSolved) {
                unsolvedQuizIds.add(quizId);
            }
        }

        if (unsolvedQuizIds.isEmpty()) {
            return null;
        }

        Random random = new Random();
        Long randomUnsolvedQuizId = unsolvedQuizIds.get(random.nextInt(unsolvedQuizIds.size()));

        StockQuiz stockQuiz = quizRepository.findById(randomUnsolvedQuizId).orElseThrow();
        return QuizResponse.builder().stockQuiz(stockQuiz).build();
    }

    public List<QuizResponse> getQuizzes() {
        return quizRepository
                .findAll()
                .stream()
                .map(QuizResponse::new)
                .collect(Collectors.toList());
    }

    public void saveSolvedQuiz(QuizRequest quizRequest) {
        // QuizRequest에서 필요한 정보 추출
        Long memberId = quizRequest.getMemberId();
        Long stockQuizId = quizRequest.getStockQuizId();
        Date solveDate = quizRequest.getSolveDate();
        Boolean isCorrect = quizRequest.getIsCorrect();

        // Member와 StockQuiz 엔티티 가져오기
        Member member = memberRepository.findById(memberId).orElseThrow();
        StockQuiz stockQuiz = quizRepository.findById(stockQuizId).orElseThrow();

        // SolvedStockQuiz 엔티티 생성
        SolvedStockQuiz solvedStockQuiz = SolvedStockQuiz
                .builder()
                .member(member)
                .stockQuiz(stockQuiz)
                .solvedDate(solveDate)
                .isCorrect(isCorrect)
                .build();

        // SolvedStockQuiz 저장
        solvedQuizRepository.save(solvedStockQuiz);
    }
}
