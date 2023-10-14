package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.GroupApplicationRequest;
import com.bull4jo.kkanbustock.group.controller.dto.GroupApprovalStatusRequest;
import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.group.repository.GroupApplicationRepository;
import com.bull4jo.kkanbustock.group.repository.GroupRepository;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupApplicationRepository groupApplicationRepository;
    private final MemberRepository memberRepository;

    public void applyGroup(GroupApplicationRequest groupApplicationRequest) {
        Long hostId = groupApplicationRequest.getHostId();
        String email = groupApplicationRequest.getEmail();
        Long guestId = getGuestId(email);

        if (guestId == null) {
            throw new IllegalArgumentException("Invalid guest email: " + email);
        }

        Member host = memberRepository.findById(hostId).orElseThrow();
        Member guest = memberRepository.findById(guestId).orElseThrow();

        GroupApplication groupApplication = GroupApplication
                .builder()
                .host(host)
                .guest(guest)
                .build();

        groupApplicationRepository.save(groupApplication);
    }

    private void changeApprovalStatus(GroupApprovalStatusRequest groupApprovalStatusRequest) {
        Long groupApplicationPk = groupApprovalStatusRequest.getGroupApplicationPk();
        String approvalStatus = groupApprovalStatusRequest.getApprovalStatus();

        GroupApplication groupApplication = groupApplicationRepository.findById(groupApplicationPk).orElseThrow();

        if (Objects.equals(approvalStatus, "승인")) {
            groupApplication.setApprovalStatus(approvalStatus);
            groupApplicationRepository.save(groupApplication);
        } else {
            // 거절 - 해당 신청 삭제
            groupApplicationRepository.delete(groupApplication);
        }
    }

    private String generateGroupName() {
        // 그룹 이름을 만들어주는 로직
        // 임시로 리턴
        return "test";
    }

    private Long getGuestId(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return member.getId();
        }
        return null;
    }

    private float getProfitRate() {
        // 수익률을 계산하는 로직
        // 임시로 리턴
        return 100;
    }

    private boolean isGroupNameExists(String name) {
        Optional<KkanbuGroup> existingGroupName = groupRepository.findByName(name);
        return existingGroupName.isPresent(); // 값이 있다면 true
    }
}
