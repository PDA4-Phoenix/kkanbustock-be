package com.bull4jo.kkanbustock.member.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService  {

    private final MemberRepository memberRepository;

    public Member getUsers(String id) {

        return memberRepository.findById(id).get();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findById(username);
        /* 디버깅 모드를 통해 여기까지 user의 Authorities()가 있음을 확인함 */

        if (!member.isPresent()) {
            throw new UsernameNotFoundException("User not found with account: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                member.get().getId(), member.get().getPassword(), member.get().getAuthorities());


    }


}
