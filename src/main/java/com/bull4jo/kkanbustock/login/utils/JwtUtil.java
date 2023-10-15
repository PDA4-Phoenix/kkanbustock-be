package com.bull4jo.kkanbustock.login.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    // 토큰의 유효성을 검증하는 메소드
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // 잘못된 서명 등의 예외 처리
        } catch (ExpiredJwtException ex) {
            // 만료된 토큰 등의 예외 처리
        }
        return false;
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // 토큰에서 사용자의 인증 정보를 얻는 메소드
    public Authentication getAuthentication(String token) {
        // 토큰에서 사용자 ID 정보 추출
        String userId = getUserIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId); // 이 메소드 명칭은 Spring Security의 표준이므로, 실제로는 ID를 기반으로 사용자 정보를 로드합니다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    // 토큰에서 사용자 ID를 가져오는 메소드
    public String getUserIdFromToken(String token) {
        // 토큰에서 클레임을 추출
        Claims claims = getClaimsFromToken(token);
        // 클레임에서 사용자 ID를 가져옴
        return claims.getId();
    }

    public Claims getClaimsFromToken(String token) {
        try {
            // 토큰을 파싱하고 클레임을 반환
            return Jwts.parser()
                    .setSigningKey(secret) // JWT를 서명할 때 사용된 키
                    .parseClaimsJws(token) // 파싱하려는 JWT 문자열
                    .getBody(); // 클레임 세트를 포함하는 페이로드 부분
        } catch (JwtException e) {
            // 유효하지 않은 토큰에 대한 예외 처리 (서명이 잘못되었거나, 토큰이 만료되었거나 등의 이유로)
            return null;
        }
    }



    // 요청 헤더에서 토큰을 해석하는 메소드
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }
}
