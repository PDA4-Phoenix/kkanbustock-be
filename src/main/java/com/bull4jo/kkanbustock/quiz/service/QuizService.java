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

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public DailyQuizResponse getDailyQuiz(String memberId) {
        boolean isSolved = isDailyQuizSolved(memberId);
        Long dailyQuizId = getDailyQuizId(memberId, isSolved);

        StockQuiz stockQuiz = quizRepository.findById(dailyQuizId).orElseThrow();
        return DailyQuizResponse.builder().stockQuiz(stockQuiz).isSolved(isSolved).build();
    }

    public SolvedStockQuizResponse getSolvedQuizzes(String memberId) {
        List<SolvedStockQuiz> solvedStockQuizzes = getSolvedQuizzesByMemberId(memberId);

        return SolvedStockQuizResponse.builder()
                .solvedStockQuizzes(solvedStockQuizzes)
                .build();
    }

    public void saveSolvedQuiz(SolvedQuizRequest solvedQuizRequest) {
        String memberId = solvedQuizRequest.getMemberId();
        Long stockQuizId = solvedQuizRequest.getStockQuizId();
        Boolean isCorrect = solvedQuizRequest.getIsCorrect();
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

    private boolean isDailyQuizSolved(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.isDailyQuizSolved();
    }

    private Long getDailyQuizId(String memberId, boolean isSolved) {
        if (isSolved) {
            // 오늘의 퀴즈를 이미 푼 상태라면 가장 최근 SolvedStockQuizId를 불러옴
            return getRecentSolvedQuizId(memberId);
        }
        // 오늘의 퀴즈를 풀지 않았다면 해당 멤버가 풀어보지 않은 퀴즈 중 하나를 불러옴
        return getUnsolvedQuizId(memberId);
    }

    private Long getRecentSolvedQuizId(String memberId) {
        List<SolvedStockQuiz> solvedQuizzes = getSolvedQuizzesByMemberId(memberId);

        solvedQuizzes.sort(Comparator.comparing(SolvedStockQuiz::getSolvedDate).reversed());
        return solvedQuizzes.get(0).getStockQuiz().getId();
    }

    private Long getUnsolvedQuizId(String memberId) {
        List<SolvedStockQuiz> solvedQuizzes = getSolvedQuizzesByMemberId(memberId);
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

    private List<SolvedStockQuiz> getSolvedQuizzesByMemberId(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.getSolvedStockQuizzes();
    }
}
