package com.bull4jo.kkanbustock.group.repository;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<KkanbuGroup,Long> {
    Optional<KkanbuGroup> findByName(String name);

    List<KkanbuGroup> findByHostId(Long memberId);

    List<KkanbuGroup> findByGuestId(Long memberId);
}
