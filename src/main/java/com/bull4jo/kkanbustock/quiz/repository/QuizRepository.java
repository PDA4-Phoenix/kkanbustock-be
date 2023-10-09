package com.bull4jo.kkanbustock.quiz.repository;

import com.bull4jo.kkanbustock.quiz.domain.entity.StockQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<StockQuiz, Long> {
}
