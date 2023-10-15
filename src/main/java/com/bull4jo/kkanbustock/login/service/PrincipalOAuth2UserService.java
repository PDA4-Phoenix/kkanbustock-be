package com.bull4jo.kkanbustock.login.service;


import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Access Token 가져오기
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        // Access Token 정보 확인
        if (accessToken != null) {
            String tokenValue = accessToken.getTokenValue();
            System.out.println("Token : " + tokenValue);
        }

        //인증서버(구글)의 정보를 가져오기
        System.out.println(userRequest.getClientRegistration());

        // 사용자 정보를 추출하고
        String email = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("name");
        String id = oAuth2User.getAttribute("sub");

//        System.out.println("Email : " + email);
//        System.out.println("Nickname : " + nickname);
//        System.out.println("ID : " + id);
        //사용자 정보 확인하기
        System.out.println(super.loadUser(userRequest).getAttributes());

       // 기존 회원 정보 조회
        Member existingMember = memberService.verifyAndRegisterMember(id, email, nickname);

        // 사용자 정보를 반환
        return oAuth2User;
    }
}





