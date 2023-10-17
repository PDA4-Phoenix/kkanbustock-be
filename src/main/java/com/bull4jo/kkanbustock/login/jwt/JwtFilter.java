package com.bull4jo.kkanbustock.login.jwt;


import com.bull4jo.kkanbustock.member.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

   private final JwtTokenProvider jwtTokenProvider;
   private final UserDetailService userDetailService;

    //    private final AdminDetailService adminDetailService;
//    private final List<String> role_admin = new ArrayList<>(Collections.singletonList("ROLE_ADMIN"));
    private final List<String> role_user = new ArrayList<>(Collections.singletonList("ROLE_USER"));

//    @Autowired
//    public JwtFilter(JwtTokenProvider jwtTokenProvider, UserDetailService userDetailService){  //, AdminDetailService adminDetailService) {
//        this.jwtTokenProvider = jwtTokenProvider;
////        this.adminDetailService = adminDetailService;
//        this.userDetailService = userDetailService;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        UserDetails userDetails = null;

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            logger.debug("OPTIONS METHOD CALLED!");
        }

        else if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            try {
                Claims claims = Jwts.parser().setSigningKey(jwtTokenProvider.getSecretKey()).parseClaimsJws(token).getBody();
                String username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (claims.get("roles").equals(role_user)) {
                        userDetails =  userDetailService.loadUserByUsername(username);
                    }
//                    else if (claims.get("roles").equals(role_admin)) {
//                        userDetails = adminDetailService.loadUserByUsername(username);
//
//                    }


                    if (jwtTokenProvider.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                    }
                }
            } catch (JwtException e) {
                logger.error("JWT validation error");
            }
        }
        filterChain.doFilter(request, response);
    }

}