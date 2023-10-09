package com.bull4jo.kkanbustock.quiz.domain.entity;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
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
    private Date solvedDate;

    @Column
    private Boolean isCorrect;

    public SolvedStockQuiz(Member member, StockQuiz stockQuiz, Date solvedDate, Boolean isCorrect) {
        this.member = member;
        this.stockQuiz = stockQuiz;
        this.solvedDate = solvedDate;
        this.isCorrect = isCorrect;
    }
}
