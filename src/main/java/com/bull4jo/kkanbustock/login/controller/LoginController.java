package com.bull4jo.kkanbustock.login.controller;

import com.bull4jo.kkanbustock.login.controller.request.AuthenticationRequest;
import com.bull4jo.kkanbustock.login.controller.request.MemberRegisterRequest;
import com.bull4jo.kkanbustock.login.controller.response.AuthenticationResponse;
import com.bull4jo.kkanbustock.login.jwt.JwtTokenProvider;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.service.MemberService;
import com.bull4jo.kkanbustock.member.service.UserDetailService;

import com.bull4jo.kkanbustock.member.service.dto.MemberRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailService userDetailService;

    @PostMapping("/register")
//    @ApiOperation(value = "회원가입")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody MemberRegisterRequest request) {

        String id = request.getId();
        System.out.println("id = " + id);
        String name = request.getName();
        System.out.println("name = " + name);

        Map<String, Object> result = this.memberService.create(request);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
//    @ApiOperation(value = "로그인 API")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        String email = authenticationRequest.getId();
        Member foundUser = memberService.findUser(email);

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.ok("Password invalid");
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(email);
        final String token = jwtTokenProvider.createToken(userDetails.getUsername(), foundUser.getRoles());
        final Member member = userDetailService.getUsers(email);

        return ResponseEntity.ok(
                new AuthenticationResponse(
                        token,
                        MemberRegisterDTO
                            .of(
                                member.getId(),
                                member.getPassword(),
                                member.getEmail(),
                                member.getName()
                            )
                )
        );
    }

    @PostMapping("/logout")
//    @ApiOperation(value = "로그 아웃 API")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 인증된 사용자의 인증 토큰 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 토큰이 존재하면 로그아웃 처리
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity.ok("로그 아웃 되었습니다.");
    }
}
