package com.bull4jo.kkanbustock.member.domain.entity;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.quiz.domain.entity.SolvedStockQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column
    private String email;

    @Column
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

    @OneToMany(mappedBy = "member")
    private List<Portfolio> portfolios;

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
