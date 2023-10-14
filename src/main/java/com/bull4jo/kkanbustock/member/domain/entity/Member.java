package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.*;
import java.util.List;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column
    private InvestorType investorType;
    @Column
    private float profitRate;

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

    @OneToMany(mappedBy = "member")
    private List<Portfolio> portfolios;

    public Member(String id, String email, String password, String nickname, InvestorType investorType, List<SolvedStockQuiz> solvedStockQuizzes, List<KkanbuGroup> hostKkanbuGroups, List<KkanbuGroup> guestKkanbuGroups, List<GroupApplication> sentGroupApplications, List<GroupApplication> receivedGroupApplications) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.investorType = investorType;
        this.solvedStockQuizzes = solvedStockQuizzes;
        this.hostKkanbuGroups = hostKkanbuGroups;
        this.guestKkanbuGroups = guestKkanbuGroups;
        this.sentGroupApplications = sentGroupApplications;
        this.receivedGroupApplications = receivedGroupApplications;
    }

    public void calculateTotalProfit() {
        float totalPurchaseAmount = 0;
        float totalEquitiesValue = 0;
        for (Portfolio portfolio : portfolios) {
            totalPurchaseAmount += portfolio.getPurchaseAmount();
            totalEquitiesValue += portfolio.getEquitiesValue();
        }
        // 0 예외 처리
        this.profitRate = (totalEquitiesValue / totalPurchaseAmount - 1) * 100;
    }

}
