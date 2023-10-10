package com.bull4jo.kkanbustock.quiz.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor
public class StockQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private String answer;

    @Column
    private String explanation;

    @OneToMany(mappedBy = "stockQuiz")
    private List<SolvedStockQuiz> solvedStockQuizzes;

}
