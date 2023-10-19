package com.bull4jo.kkanbustock.member.controller;

import com.bull4jo.kkanbustock.member.domain.entity.InvestorType;
import com.bull4jo.kkanbustock.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/investor-type")
    public ResponseEntity<String> saveInvestorType(@RequestParam String memberId, @RequestParam InvestorType investorType) {
        return ResponseEntity.ok(memberService.saveInvestorType(memberId, investorType));
    }
}
