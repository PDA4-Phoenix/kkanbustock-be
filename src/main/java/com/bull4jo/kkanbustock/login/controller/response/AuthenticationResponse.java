package com.bull4jo.kkanbustock.login.controller.response;

import com.bull4jo.kkanbustock.member.domain.entity.Member;

public class AuthenticationResponse {

    private final String jwt;
    private final Member member;

    public AuthenticationResponse(String jwt, Member member) {
        this.jwt = jwt;
        this.member = member;
    }
}
