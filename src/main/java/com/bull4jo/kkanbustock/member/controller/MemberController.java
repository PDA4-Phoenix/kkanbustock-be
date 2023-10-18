package com.bull4jo.kkanbustock.member.controller;

import com.bull4jo.kkanbustock.member.domain.entity.InvestorType;
import com.bull4jo.kkanbustock.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/investor-type")
    public String saveInvestorType(@RequestParam String memberId, @RequestParam InvestorType investorType) {
        return memberService.saveInvestorType(memberId, investorType);
    }
}
