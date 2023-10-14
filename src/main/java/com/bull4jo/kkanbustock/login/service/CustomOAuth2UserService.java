package com.bull4jo.kkanbustock.login.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            // 예외 처리 로직 (로그 기록, 오류 메시지 전달 등)
            throw new UsernameNotFoundException("OAuth2 사용자를 처리하는 동안 오류 발생", ex);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // OAuth2 제공자의 등록 ID(예: "google")를 가져옵니다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 제공자로부터 가져온 사용자의 속성
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Member member;
        if ("google".equals(registrationId)) {
            member = Member.builder()
                    .id((String) attributes.get("id"))
                    .email((String) attributes.get("email"))
                    .nickname((String) attributes.get("name"))
                    .build();
        } else {
            throw new RuntimeException("지원하지 않는 소셜 로그인 타입입니다.");
        }

        log.info("id = {}", member.getId());
        log.info("email = {}", member.getEmail());
        log.info("nickname = {}", member.getNickname());

        // 기존 시스템에서 사용자를 조회하거나, 존재하지 않는 경우 새로 생성하는 로직을 추가할 수 있습니다.
        // 이 예제에서는 단순히 UserDetails 객체를 반환합니다.
        // 실제 시나리오에서는 이 부분에 사용자 데이터베이스 조회/생성 로직이 들어갑니다.

        // UserDetails 객체 생성
        UserDetails userDetails = User.builder()
                .username(member.getId())
                .password("") // OAuth2 인증에서는 비밀번호가 필요하지 않습니다.
                .authorities(oAuth2User.getAuthorities())
                .build();

        return new DefaultOAuth2User(
                userDetails.getAuthorities(),
                userDetails.getAttributes(),
                "id" // 사용자의 식별자가 되는 속성의 이름. 여기서는 "id"를 사용합니다.
        );
    }
}
