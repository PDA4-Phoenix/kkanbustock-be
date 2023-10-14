package com.bull4jo.kkanbustock.quiz.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.quiz.controller.dto.DailyQuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedDailyQuizResponse;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import com.bull4jo.kkanbustock.quiz.controller.dto.QuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.QuizResponse;
import com.bull4jo.kkanbustock.quiz.repository.QuizRepository;
import com.bull4jo.kkanbustock.quiz.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public QuizResponse getDailyQuiz(DailyQuizRequest dailyQuizRequest) {
        Long memberId = dailyQuizRequest.getMemberId();

        if (isDailyQuizSolved(memberId)) {
            // 오늘의 퀴즈를 이미 푼 상태라면 가장 최근 SolvedStockQuiz를 불러옴
            Long recentSolvedQuizId = getRecentSolvedQuizId(memberId);

            StockQuiz stockQuiz = quizRepository.findById(recentSolvedQuizId).orElseThrow();
            return QuizResponse.builder().stockQuiz(stockQuiz).build();
        }

        // 오늘의 퀴즈를 풀지 않았다면 해당 멤버가 풀어보지 않은 퀴즈 중 하나를 불러옴
        Long dailyQuizId = selectDailyQuizId(memberId);

        if (dailyQuizId == null) {
            // 사용자가 모든 퀴즈를 다 풀어 본 경우
            return null;
        }

        StockQuiz stockQuiz = quizRepository.findById(dailyQuizId).orElseThrow();
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
        Long memberId = quizRequest.getMemberId();
        Long stockQuizId = quizRequest.getStockQuizId();
        Boolean isCorrect = quizRequest.getIsCorrect();
        LocalDateTime solvedDate = LocalDateTime.now();

        Member member = memberRepository.findById(memberId).orElseThrow();
        StockQuiz stockQuiz = quizRepository.findById(stockQuizId).orElseThrow();

        SolvedStockQuiz solvedStockQuiz = SolvedStockQuiz
                .builder()
                .member(member)
                .stockQuiz(stockQuiz)
                .solvedDate(solvedDate)
                .isCorrect(isCorrect)
                .build();

        solvedQuizRepository.save(solvedStockQuiz);
        member.setDailyQuizSolved(true);
    }

    private boolean isDailyQuizSolved(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.isDailyQuizSolved();
    }

    private Long getRecentSolvedQuizId(Long memberId) {
        List<SolvedStockQuiz> solvedQuizzes = solvedQuizRepository.findByMemberId(memberId);

        solvedQuizzes.sort(Comparator.comparing(SolvedStockQuiz::getSolvedDate).reversed());
        return solvedQuizzes.get(0).getStockQuiz().getId();
    }

    private Long selectDailyQuizId(Long memberId) {
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

        return unsolvedQuizIds.get(random.nextInt(unsolvedQuizIds.size()));
    }
}
