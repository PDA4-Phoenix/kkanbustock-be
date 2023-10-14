package com.bull4jo.kkanbustock.group.repository;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<KkanbuGroup, KkanbuGroupPK> {
    Optional<KkanbuGroup> findByName(String name);
    Optional<KkanbuGroup> findByInviteCode(String inviteCode);

    List<KkanbuGroup> findByHostId(KkanbuGroupPK memberId);

    List<KkanbuGroup> findByGuestId(KkanbuGroupPK memberId);
}
