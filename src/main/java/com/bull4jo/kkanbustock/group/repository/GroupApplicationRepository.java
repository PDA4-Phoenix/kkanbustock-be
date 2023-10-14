package com.bull4jo.kkanbustock.group.repository;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupApplicationRepository extends JpaRepository<GroupApplication, Long> {
    List<GroupApplication> findByGuestId(Long guestId);
}
