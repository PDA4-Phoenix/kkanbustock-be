package com.bull4jo.kkanbustock.quiz.repository;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedStockQuiz, Long> {
    @Query("SELECT q.stockQuiz.id FROM SolvedStockQuiz q WHERE q.member.id = :memberId ORDER BY q.solvedDate DESC")
    Optional<List<Long>> getRecentSolvedStockQuizByMemberId(String memberId);

    @Query("SELECT q.id FROM StockQuiz q LEFT JOIN SolvedStockQuiz sq ON q.id = sq.stockQuiz.id AND sq.member.id = :memberId WHERE sq.stockQuiz.id IS NULL")
    Optional<List<Long>> getUnSolvedQuizIdByMemberId(String memberId);
}