package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
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
    private String id;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private InvestorType investorType;

    @OneToMany(mappedBy = "member")
    private List<SolvedStockQuiz> solvedStockQuizzes;

    @OneToMany(mappedBy = "member")
    private List<KkanbuGroup> kkanbuGroups;

    public Member(Long id, String password, String nickname, InvestorType investorType, List<SolvedStockQuiz> solvedStockQuizzes, List<KkanbuGroup> kkanbuGroups) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.investorType = investorType;
        this.solvedStockQuizzes = solvedStockQuizzes;
        this.kkanbuGroups = kkanbuGroups;
    }
}
