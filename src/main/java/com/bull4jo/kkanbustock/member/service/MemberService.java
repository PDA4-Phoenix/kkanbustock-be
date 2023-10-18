package com.bull4jo.kkanbustock.member.service;

import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.login.controller.request.MemberRegisterRequest;
import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.member.domain.entity.InvestorType;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import com.bull4jo.kkanbustock.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioService portfolioService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String saveInvestorType(final String memberId, final InvestorType investorType) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setInvestorType(investorType);
        return memberId;
    }

    @Transactional
    public Map<String, Object> create(MemberRegisterRequest request) {

        Map<String, Object> resultMap = new HashMap<>();

        // 아이디가 중복되었을 때
        if (memberRepository.findById(request.getAccount()).isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "중복된 아이디 입니다.");
            return resultMap;
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member newMember =
                Member.builder()
                        .id(request.getAccount())
                        .password(encodedPassword)
                        .name(request.getName())
                        .email(request.getEmail())
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build();

        try {
            memberRepository.save(newMember);
            portfolioService.setRandomPortfolio(newMember.getId());
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    public Member findUser(String id) {
        return memberRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }



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
