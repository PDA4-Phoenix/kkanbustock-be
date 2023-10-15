package com.bull4jo.kkanbustock.login.utils;


import com.bull4jo.kkanbustock.login.service.PrincipalOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOAuth2UserService principalOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // 필요에 따라 CSRF 보호를 비활성화 할 수 있습니다.
                .authorizeRequests()
                .antMatchers("/", "/login**", "/error**").permitAll() // 이 경로는 인증 없이 접근 가능합니다.
                .anyRequest().authenticated() // 이외의 모든 요청은 인증을 필요로 합니다.
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login") // 사용자 정의 로그인 페이지 URL.
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/google") // 인증 코드를 수신할 엔드포인트.
                .and()
                .userInfoEndpoint()
                .userService(principalOAuth2UserService); // 소셜 로그인 성공 후, 사용자 정보를 가져오는 서비스.
    }
}