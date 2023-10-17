package com.bull4jo.kkanbustock.login.controller.response;

import com.bull4jo.kkanbustock.member.service.dto.MemberRegisterDTO;
import lombok.Getter;

@Getter
public class AuthenticationResponse {

    private final String jwt;
    private final MemberRegisterDTO member;

    public AuthenticationResponse(String jwt, MemberRegisterDTO member) {
        this.jwt = jwt;
        this.member = member;
    }
}
