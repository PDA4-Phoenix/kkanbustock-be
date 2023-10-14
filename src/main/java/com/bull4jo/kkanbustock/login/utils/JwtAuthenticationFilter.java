package com.bull4jo.kkanbustock.login.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private JwtUtil jwtUtil; // JwtUtil 빈을 주입

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //헤더에서 JWT 받아옴
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        //유효한 토큰인지 확인
        if (token != null && jwtUtil.validateToken(token)) {
            //토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
            Authentication auth = jwtUtil.getAuthentication(token);
            //SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
