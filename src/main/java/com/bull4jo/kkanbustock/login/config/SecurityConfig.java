package com.bull4jo.kkanbustock.login.config;

import com.bull4jo.kkanbustock.login.jwt.JwtFilter;
import com.bull4jo.kkanbustock.login.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 무조건 open 되는 endpoint
    private static final String[] PUBLIC_URI = {
            "/api/v1/**"
    };

    private static final String[] ADMIN_URI = {
//      /서비스명/admin/**" 이렇게 지어서 서비스명 하위 도메인은 ROLE_ADMIN만 접근하게 설정
    };

    private static final String[] USER_URI = {
//      /서비스명/users/**" 이렇게 지어서 서비스명 하위 도메인은 ROLE_USER만 접근하게 설정

    };

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /* 맨 아래 configure를 하기 전에 먼저 실행되는 함수 */
    /* PUBLIC_URI에 모든 접근을 허용하기 위해 검증을 ignoring */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(PUBLIC_URI);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 세션 사용 X
                .authorizeRequests() // 요청에 대한 사용 권한 체크
                .antMatchers("/api/v1/**").hasRole("USER")
                .antMatchers(ADMIN_URI).hasRole("ADMIN")
                .antMatchers(USER_URI).hasRole("USER")
                .antMatchers(HttpMethod.POST).authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
