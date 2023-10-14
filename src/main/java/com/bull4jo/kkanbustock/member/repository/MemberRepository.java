package com.bull4jo.kkanbustock.member.repository;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{

    Member findByEmail(String email);
}