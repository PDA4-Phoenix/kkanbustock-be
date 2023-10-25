package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String id;  // account

    @Column
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column
    private InvestorType investorType;

    @Column
    private float profitRate;

    @Column(nullable = false)
    private boolean isDailyQuizSolved; // 매일 자정마다 false로 초기화 해주기

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Member(String id, String email, String password, String name, InvestorType investorType, boolean isDailyQuizSolved, List<SolvedStockQuiz> solvedStockQuizzes, List<KkanbuGroup> hostKkanbuGroups, List<KkanbuGroup> guestKkanbuGroups, List<GroupApplication> sentGroupApplications, List<GroupApplication> receivedGroupApplications) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.investorType = investorType;
        this.isDailyQuizSolved = isDailyQuizSolved;
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

    public void setProfitRate(float profitRate) {
        this.profitRate = profitRate;
    }

    public void setDailyQuizSolved(boolean dailyQuizSolved) {
        isDailyQuizSolved = dailyQuizSolved;
    }
}
