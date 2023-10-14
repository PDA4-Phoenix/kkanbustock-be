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
    private String id;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private InvestorType investorType;

    @Column
    private boolean isDailyQuizSolved; // 매일 자정마다 false로 초기화 해주기

    @OneToMany(mappedBy = "member")
    private List<SolvedStockQuiz> solvedStockQuizzes;

    public Member(String id, String password, String nickname, InvestorType investorType, boolean isDailyQuizSolved, List<SolvedStockQuiz> solvedStockQuizzes) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.investorType = investorType;
        this.isDailyQuizSolved = isDailyQuizSolved;
        this.solvedStockQuizzes = solvedStockQuizzes;
    }

    public void setDailyQuizSolved(boolean dailyQuizSolved) {
        isDailyQuizSolved = dailyQuizSolved;
    }
}
