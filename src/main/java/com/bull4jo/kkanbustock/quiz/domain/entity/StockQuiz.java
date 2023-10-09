package com.bull4jo.kkanbustock.quiz.domain.entity;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
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

    public StockQuiz(Long id, String content, String answer, String explanation, List<SolvedStockQuiz> solvedStockQuizzes) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.explanation = explanation;
        this.solvedStockQuizzes = solvedStockQuizzes;
    }
}
