package com.bull4jo.kkanbustock.quiz.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public StockQuiz(Long id, String content, String answer, String explanation) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.explanation = explanation;
    }
}
