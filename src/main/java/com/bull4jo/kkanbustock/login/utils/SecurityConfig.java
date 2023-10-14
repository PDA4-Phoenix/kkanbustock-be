//package com.bull4jo.kkanbustock.login.utils;
//
//
//import com.bull4jo.kkanbustock.login.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private LoginService loginService;  // 이 서비스는 사용자 정보를 로드하는 데 사용됩니다.
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()  // 필요에 따라 CSRF 보호를 비활성화 할 수 있습니다.
//                .authorizeRequests()
//                .antMatchers("/", "/login**", "/error**").permitAll() // 이 경로는 인증 없이 접근 가능합니다.
//                .anyRequest().authenticated() // 이외의 모든 요청은 인증을 필요로 합니다.
//                .and()
//                .oauth2Login()
//                .loginPage("/login") // 사용자 정의 로그인 페이지 URL.
//                .redirectionEndpoint()
//                .baseUri("/login/oauth2/code/*") // 인증 코드를 수신할 엔드포인트.
//                .and()
//                .userInfoEndpoint()
//                .userService(loginService) // 소셜 로그인 성공 후, 사용자 정보를 가져오는 서비스.
//                .and()
//                .successHandler(new AuthenticationSuccessHandler() { // 인증에 성공한 경우 수행될 작업.
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        // 인증에 성공한 후 원하는 작업을 수행합니다.
//                        // 예를 들어, 사용자를 사용자의 대시보드나 홈 페이지로 리디렉션할 수 있습니다.
//                        response.sendRedirect("/dashboard");
//                    }
//                });
//        // 기타 설정들...
//    }
//}