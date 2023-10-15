package com.bull4jo.kkanbustock.group.repository;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupApplicationRepository extends JpaRepository<GroupApplication, KkanbuGroupPK> {
    List<GroupApplication> findByGuestId(String guestId);

    Optional<List<GroupApplication>> findByGuestIdAndApprovalStatus(String guestId, boolean approvalStatus);
}
