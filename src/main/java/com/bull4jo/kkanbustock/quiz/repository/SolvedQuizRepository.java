package com.bull4jo.kkanbustock.quiz.repository;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedStockQuiz, Long> {
}
