package com.bull4jo.kkanbustock.login.controller.request;

import com.bull4jo.kkanbustock.member.service.dto.MemberRegisterDTO;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemberRegisterRequest {

    @NotNull
    private String account;   // 로그인 할 때 쓰는 아이디

    @NotNull
    private String password;

    private String email;
    private String name;

    public MemberRegisterDTO toServiceDto(){
        return MemberRegisterDTO.of(account, password, email, name);
    }

}
