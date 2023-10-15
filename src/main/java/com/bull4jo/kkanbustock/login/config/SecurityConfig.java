package com.bull4jo.kkanbustock.login.config;


import com.bull4jo.kkanbustock.login.jwt.JwtAuthenticationFilter;
import com.bull4jo.kkanbustock.login.jwt.JwtTokenProvider;
import com.bull4jo.kkanbustock.login.oauth2.OAuth2AuthenticationFailureHandler;
import com.bull4jo.kkanbustock.login.oauth2.OAuth2AuthenticationSuccessHandler;
import com.bull4jo.kkanbustock.login.service.PrincipalOAuth2UserService;
import com.bull4jo.kkanbustock.login.repository.CookieAuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOAuth2UserService principalOauth2UserService;

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //httpBasic, csrf, formLogin, rememberMe, logout, session disable
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()  // csrf 미사용
                .formLogin().disable()  // 로그인 폼 미사용
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션을 사용하지 않음


        //요청에 대한 권한 설정
        http.authorizeRequests()
                .antMatchers( "/oauth2/**")
                .permitAll()
                .anyRequest().authenticated();

        //oauth2Login
        http.oauth2Login()
                .authorizationEndpoint()  // 소셜 로그인 url
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)  // 인증 요청을 cookie 에 저장
                .and()
                .redirectionEndpoint()  // 소셜 인증 후 redirect url
                .and()
                .userInfoEndpoint()
                .userService(principalOauth2UserService) // 회원 정보 처리
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        //jwt filter 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);


    }
}