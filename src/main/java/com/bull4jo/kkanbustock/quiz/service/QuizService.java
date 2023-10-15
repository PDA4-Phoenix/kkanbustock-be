package com.bull4jo.kkanbustock.quiz.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedStockQuizResponse;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import com.bull4jo.kkanbustock.quiz.controller.dto.SolvedQuizRequest;
import com.bull4jo.kkanbustock.quiz.controller.dto.DailyQuizResponse;
import com.bull4jo.kkanbustock.quiz.repository.QuizRepository;
import com.bull4jo.kkanbustock.quiz.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    @Transactional(readOnly = true)
    public DailyQuizResponse getDailyQuiz(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        boolean isSolved = member.isDailyQuizSolved();
        Long dailyQuizId;
        if (isSolved) {
            // 오늘의 퀴즈를 이미 푼 상태라면 가장 최근 SolvedStockQuizId를 불러옴
            dailyQuizId = solvedQuizRepository.getRecentSolvedStockQuizByMemberId(memberId).get(0);
        } else {
            // 오늘의 퀴즈를 풀지 않았다면 해당 멤버가 풀어보지 않은 퀴즈 중 하나를 불러옴
            dailyQuizId = solvedQuizRepository.getUnSolvedQuizIdByMemberId(memberId).orElseThrow().get(0);
        }

        StockQuiz stockQuiz = quizRepository.findById(dailyQuizId).orElseThrow();
        return DailyQuizResponse
                .builder()
                .stockQuiz(stockQuiz)
                .isSolved(isSolved)
                .build();
    }

    @Transactional(readOnly = true)
    public SolvedStockQuizResponse getSolvedQuizzes(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        // 예외처리 필요
        List<SolvedStockQuiz> solvedStockQuizzes = member.getSolvedStockQuizzes();

        return SolvedStockQuizResponse.builder()
                .solvedStockQuizzes(solvedStockQuizzes)
                .build();
    }

    @Transactional
    public Long saveSolvedQuiz(SolvedQuizRequest solvedQuizRequest) {
        String memberId = solvedQuizRequest.getMemberId();
        Long stockQuizId = solvedQuizRequest.getStockQuizId();
        Boolean isCorrect = solvedQuizRequest.getIsCorrect();
        LocalDateTime solvedDate = LocalDateTime.now();

        Member member = memberRepository.findById(memberId).orElseThrow();
        if (member.isDailyQuizSolved()) {
            // 나중에 변경해주세요
            throw new RuntimeException("이미 데일리 퀴즈 플었습니다.");
        }

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

        return stockQuizId;
    }

}
