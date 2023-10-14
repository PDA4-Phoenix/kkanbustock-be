package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.*;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private InvestorType investorType;

    @OneToMany(mappedBy = "member")
    private List<SolvedStockQuiz> solvedStockQuizzes;

    @OneToMany(mappedBy = "host")
    private List<KkanbuGroup> hostKkanbuGroups;

    @OneToMany(mappedBy = "guest")
    private List<KkanbuGroup> guestKkanbuGroups;

    @OneToMany(mappedBy = "host")
    private List<GroupApplication> sentGroupApplications;

    @OneToMany(mappedBy = "guest")
    private List<GroupApplication> receivedGroupApplications;

    public Member(Long id, String email, String password, String nickname, InvestorType investorType, List<SolvedStockQuiz> solvedStockQuizzes, List<KkanbuGroup> hostKkanbuGroups, List<KkanbuGroup> guestKkanbuGroups, List<GroupApplication> sentGroupApplications, List<GroupApplication> receivedGroupApplications) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.investorType = investorType;
        this.solvedStockQuizzes = solvedStockQuizzes;
        this.hostKkanbuGroups = hostKkanbuGroups;
        this.guestKkanbuGroups = guestKkanbuGroups;
        this.sentGroupApplications = sentGroupApplications;
        this.receivedGroupApplications = receivedGroupApplications;
    }
}
