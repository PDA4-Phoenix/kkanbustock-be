package com.bull4jo.kkanbustock.quiz.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import com.bull4jo.kkanbustock.quiz.domain.dto.QuizRequest;
import com.bull4jo.kkanbustock.quiz.domain.dto.QuizResponse;
import com.bull4jo.kkanbustock.quiz.repository.QuizRepository;
import com.bull4jo.kkanbustock.quiz.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public QuizResponse getQuiz(long quizId) {
        StockQuiz stockQuiz = quizRepository.findById(quizId).orElseThrow();
        return QuizResponse.of(stockQuiz);
    }

    public List<QuizResponse> getQuizzes() {
        List<StockQuiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(QuizResponse::of).toList();
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
