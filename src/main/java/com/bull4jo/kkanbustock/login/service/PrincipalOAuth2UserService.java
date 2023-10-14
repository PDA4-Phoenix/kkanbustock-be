package com.bull4jo.kkanbustock.login.service;


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOAuth2DetailService extends DefaultOAuth2UserService {

    @Override // 구글소셜로그인 후 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest);
        System.out.println("getClientRegistration() = " + userRequest.getClientRegistration());
        System.out.println("getAccessToken = " + userRequest.getAccessToken().getTokenValue());
        // 구글로그인 버튼클릭 -> 구글로그인 창 -> 로그인을 완료 -> code를 return(OAuth-Client라이브러리) -> code를 사용해서 AccessToken을 요청해서 받는다.
        // 여기까지가 userRequest정보이다. -> loadUser() 함수호출 -> 구글로부터 회원프로필 얻을 수 있다.(ex. email, family_name 등등)

        System.out.println("getAttributes() = " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
