package com.bull4jo.kkanbustock.login.utils;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 이메일 대신 ID를 사용하여 사용자 조회
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + id + " not found"));

        // 비밀번호 대신 "N/A" 사용 (비밀번호가 없는 경우)
        return new org.springframework.security.core.userdetails.User(member.getId(), "N/A", Collections.emptyList());
    }
}

