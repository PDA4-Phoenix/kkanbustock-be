package com.bull4jo.kkanbustock.login.service;

import com.bull4jo.kkanbustock.login.utils.JwtUtil;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Autowired
    public AuthService(JwtUtil jwtUtil, MemberService memberService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
    }

    public String authenticateAndGenerateToken(String id,String email, String nickname) {
        Member member = memberService.verifyAndRegisterMember(id, email, nickname);
        if (member != null) {
            return jwtUtil.generateToken(member.getId());
        }
        return null;
        }
        }