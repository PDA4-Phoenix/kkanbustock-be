package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private InvestorType investorType;

    @OneToMany(mappedBy = "member")
    private List<SolvedStockQuiz> solvedStockQuizzes;

    @Builder
    public Member(String id, String email, String nickname, InvestorType investorType, List<SolvedStockQuiz> solvedStockQuizzes) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.investorType = investorType;
        this.solvedStockQuizzes = solvedStockQuizzes;
    }
}
