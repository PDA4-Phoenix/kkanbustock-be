package com.bull4jo.kkanbustock.member.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Member(Long id, String password, String nickname, InvestorType investorType) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.investorType = investorType;
    }
}
