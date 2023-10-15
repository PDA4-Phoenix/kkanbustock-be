//package com.bull4jo.kkanbustock.login.service;
//
//import com.bull4jo.kkanbustock.login.jwt.JwtTokenProvider;
//import com.bull4jo.kkanbustock.member.domain.entity.Member;
//import com.bull4jo.kkanbustock.member.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//@Service
//public class AuthService {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final MemberService memberService;
//
//    @Autowired
//    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.memberService = memberService;
//    }
//
//    public String authenticateAndGenerateToken(String id,String email, String nickname) {
//        Member member = memberService.verifyAndRegisterMember(id, email, nickname);
//        if (member != null) {
//            return jwtTokenProvider.generateToken(member.getEmail());
//        }
//        return null;
//        }
//}