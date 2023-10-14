package com.bull4jo.kkanbustock.login.controller;

import com.bull4jo.kkanbustock.login.service.AuthService;
import com.bull4jo.kkanbustock.login.service.LoginService;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {

    LoginService  loginService;
//    private final MemberService memberService;
    private final AuthService authService;

    public LoginController(LoginService loginService, AuthService authService) {

        this.loginService = loginService;
//        this.memberService = memberService;
        this.authService = authService;
    }

    @GetMapping("/code/{registrationId}")
    public ResponseEntity<String> googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        Member member = loginService.socialLogin(code, registrationId);
//        Member registeredMember = memberService.verifyAndRegisterMember(member.getId(), member.getEmail(), member.getNickname());
//        return ResponseEntity.ok(registeredMember);
        String token = authService.authenticateAndGenerateToken(member.getId(),member.getEmail(), member.getNickname());

        if (token != null) {
//            HttpHeaders responseHeaders = new HttpHeaders();
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Authentication failed.");
        }

    }

}