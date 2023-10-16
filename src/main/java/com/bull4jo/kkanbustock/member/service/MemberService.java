package com.bull4jo.kkanbustock.member.service;

import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;


    @Scheduled (cron = "0 2 2 * * *")
    @Transactional
    public void updateMemberProfitRate() {
        float totalPurchaseAmount = 0;
        float totalEquitiesValue = 0;
        List<Member> members = memberRepository.findAll();

        portfolioRepository.calculateTotalPurchaseAmountByMemberId(members.get(0).getId());
        portfolioRepository.calculateTotalEquitiesValueByMemberId(members.get(0).getId());
        for (Member member : members) {
            Float purchaseAmount = portfolioRepository
                    .calculateTotalPurchaseAmountByMemberId(member.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_TOTAL_PURCHASE_AMOUNT));

            Float totalEquities = portfolioRepository
                    .calculateTotalEquitiesValueByMemberId(member.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_EQUITIES_VALUE_AMOUNT));

            float profitRate = (totalEquities / purchaseAmount - 1) * 100;
            member.setProfitRate(profitRate);
        }

        // 메서드 실행 시작 로그
        System.out.println("updateMemberProfitRate() 메서드가 실행됩니다.");

    }
}
