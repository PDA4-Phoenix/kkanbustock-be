package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
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
    private Long id;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private InvestorType investorType;

    @OneToMany(mappedBy = "member")
    private List<SolvedStockQuiz> solvedStockQuizzes;

    public Member(Long id, String password, String nickname, InvestorType investorType, List<SolvedStockQuiz> solvedStockQuizzes) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.investorType = investorType;
        this.solvedStockQuizzes = solvedStockQuizzes;
    }
}
