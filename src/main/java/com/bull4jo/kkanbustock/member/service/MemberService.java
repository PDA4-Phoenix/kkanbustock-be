package com.bull4jo.kkanbustock.member.service;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Setter
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member verifyAndRegisterMember(String id, String email, String nickname) {
        // id를 통해 기존 회원 정보 조회
        Optional<Member> existingMember = memberRepository.findById(id);

        // 기존 회원 정보가 있으면 반환
        if (existingMember.isPresent()) {

            System.out.println("existingMember= " + existingMember.get());
            System.out.println("회원 정보가 이미 있습니다.");

            return existingMember.get();
        }

        // 새 회원 정보 생성
        Member newMember = new Member();
        newMember.setId(id);
        newMember.setEmail(email);
        newMember.setNickname(nickname);

        // 새 회원 정보 저장
        memberRepository.save(newMember);

        // 새 회원 정보 저장 및 반환
        return newMember;
    }
}
