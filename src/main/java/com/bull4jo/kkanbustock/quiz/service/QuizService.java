package com.bull4jo.kkanbustock.quiz.service;

import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    @Transactional(readOnly = true)
    public DailyQuizResponse getDailyQuiz(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        boolean isSolved = member.isDailyQuizSolved();
        Long dailyQuizId;
        if (isSolved) {
            // 오늘의 퀴즈를 이미 푼 상태라면 가장 최근 SolvedStockQuizId를 불러옴
            dailyQuizId = solvedQuizRepository.getRecentSolvedStockQuizByMemberId(memberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.SOLVED_QUIZ_NOT_FOUND)).get(0);
        } else {
            // 오늘의 퀴즈를 풀지 않았다면 해당 멤버가 풀어보지 않은 퀴즈 중 하나를 불러옴
            dailyQuizId = solvedQuizRepository.getUnSolvedQuizIdByMemberId(memberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.UNSOLVED_QUIZ_NOT_FOUND)).get(0);
        }

        StockQuiz stockQuiz = quizRepository.findById(dailyQuizId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUIZ_NOT_FOUND));
        return DailyQuizResponse
                .builder()
                .stockQuiz(stockQuiz)
                .isSolved(isSolved)
                .build();
    }

    @Transactional(readOnly = true)
    public List<SolvedStockQuizResponse> getSolvedQuizzes(String memberId, Pageable pageable) {
        Page<SolvedStockQuiz> solvedStockQuizPage = solvedQuizRepository.findByMemberId(memberId, pageable)
                .orElseThrow(() -> new CustomException(ErrorCode.SOLVED_QUIZ_NOT_FOUND));

        return solvedStockQuizPage
                .getContent()
                .stream()
                .map(solvedStockQuiz ->
                        SolvedStockQuizResponse.builder()
                                .stockQuiz(solvedStockQuiz.getStockQuiz())
                                .solvedStockQuiz(solvedStockQuiz)
                                .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public Long saveSolvedQuiz(SolvedQuizRequest solvedQuizRequest) {

        String memberId = solvedQuizRequest.getMemberId();
        Long stockQuizId = solvedQuizRequest.getStockQuizId();
        Boolean isCorrect = solvedQuizRequest.getIsCorrect();
        LocalDateTime solvedDate = LocalDateTime.now();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.isDailyQuizSolved()) {
            throw new CustomException(ErrorCode.DAILY_QUIZ_FORBIDDEN);
        }

        StockQuiz stockQuiz = quizRepository.findById(stockQuizId).orElseThrow(() -> new CustomException(ErrorCode.QUIZ_NOT_FOUND));
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
