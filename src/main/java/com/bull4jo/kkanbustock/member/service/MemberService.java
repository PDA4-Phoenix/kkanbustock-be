package com.bull4jo.kkanbustock.member.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public void updateMemberProfitRate() {
        float totalPurchaseAmount = 0;
        float totalEquitiesValue = 0;
        List<Member> members = memberRepository.findAll();

        portfolioRepository.calculateTotalPurchaseAmountByMemberId(members.get(0).getId());
        portfolioRepository.calculateTotalEquitiesValueByMemberId(members.get(0).getId());
        for (Member member : members) {
            Float purchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(member.getId());
            Float totalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(member.getId());

            float profitRate = (totalEquities / purchaseAmount - 1) * 100;
            member.setProfitRate(profitRate);
        }
    }
}
