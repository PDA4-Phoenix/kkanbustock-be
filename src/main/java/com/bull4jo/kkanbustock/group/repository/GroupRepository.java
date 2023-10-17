package com.bull4jo.kkanbustock.group.repository;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<KkanbuGroup, KkanbuGroupPK> {
    @Query("SELECT p FROM KkanbuGroup p WHERE p.host.id = :memberId OR p.guest.id = :memberId")
    Optional<List<KkanbuGroup>> findAllByHostIdOrGuestId(String memberId);

    Optional<KkanbuGroup> findByKkanbuGroupPK(KkanbuGroupPK kkanbuGroupPK);

    @Query(value = "SELECT * FROM kkanbu_group k ORDER BY k.profit_rate DESC ",nativeQuery = true)
    Optional<List<KkanbuGroup>> findTopByOrderByProfitRateDesc();
}
