package com.bull4jo.kkanbustock.quiz.repository;

import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<StockQuiz, Long> {
    @Query("SELECT q.stockQuiz FROM SolvedStockQuiz q WHERE q.member.id = :memberId")
    Optional<List<StockQuiz>> getSolvedStockQuizByMemberId(String memberId);
}
