package com.bull4jo.kkanbustock.member.service;

import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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
        return memberRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new User(
                member.getId(), member.getPassword(), member.getAuthorities());
    }


}
