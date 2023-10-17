package com.bull4jo.kkanbustock.member.service.dto;


import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDTO {

    private String id;   // 로그인 할 때 쓰는 아이디
    private String password;
    private String name;
    private String email;

    public static MemberRegisterDTO of(String id, String password, String email, String name){
        return new MemberRegisterDTO(id, password, email, name);
    }
}
