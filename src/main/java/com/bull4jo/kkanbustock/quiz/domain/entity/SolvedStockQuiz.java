package com.bull4jo.kkanbustock.quiz.domain.entity;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
@Getter
@NoArgsConstructor
public class SolvedStockQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn
    private StockQuiz stockQuiz;

    @Column
    private LocalDateTime solvedDate;

    @Column
    private Boolean isCorrect;

    @Builder
    public SolvedStockQuiz(Long id, Member member, StockQuiz stockQuiz, LocalDateTime solvedDate, Boolean isCorrect) {
        this.id = id;
        this.member = member;
        this.stockQuiz = stockQuiz;
        this.solvedDate = solvedDate;
        this.isCorrect = isCorrect;
    }
}
