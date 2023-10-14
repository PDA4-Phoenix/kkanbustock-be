package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.GroupApplicationRequest;
import com.bull4jo.kkanbustock.group.controller.dto.GroupApprovalStatusRequest;
import com.bull4jo.kkanbustock.group.controller.dto.GroupResponse;
import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.group.repository.GroupApplicationRepository;
import com.bull4jo.kkanbustock.group.repository.GroupRepository;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupApplicationRepository groupApplicationRepository;
    private final MemberRepository memberRepository;

    public List<GroupResponse> getGroups() {
        return groupRepository
                .findAll()
                .stream()
                .map(GroupResponse::new)
                .collect(Collectors.toList());
    }

    public GroupResponse getGroup(Long kkanbuGroupPk) {
        KkanbuGroup kkanbuGroup = groupRepository.findById(kkanbuGroupPk).orElseThrow();
        return new GroupResponse(kkanbuGroup);
    }

    public void applyGroup(GroupApplicationRequest groupApplicationRequest) {
        String email = groupApplicationRequest.getEmail();
        Long guestId = getGuestId(email);

        if (guestId == null) {
            throw new IllegalArgumentException("Invalid guest email: " + email);
        }

        Long hostId = groupApplicationRequest.getHostId();
        LocalDateTime createdDate = LocalDateTime.now();

        Member host = memberRepository.findById(hostId).orElseThrow();
        Member guest = memberRepository.findById(guestId).orElseThrow();

        GroupApplication groupApplication = GroupApplication
                .builder()
                .host(host)
                .guest(guest)
                .createdDate(createdDate)
                .build();

        groupApplicationRepository.save(groupApplication);
    }

    public void changeApprovalStatus(GroupApprovalStatusRequest groupApprovalStatusRequest) {
        Long groupApplicationPk = groupApprovalStatusRequest.getGroupApplicationPk();
        boolean approvalStatus = groupApprovalStatusRequest.isApprovalStatus();

        GroupApplication groupApplication = groupApplicationRepository.findById(groupApplicationPk).orElseThrow();

        if (approvalStatus) {
            createGroup(groupApplicationPk);
            groupApplicationRepository.delete(groupApplication);
        } else {
            groupApplicationRepository.delete(groupApplication);
        }
    }

    private void createGroup(Long groupApplicationPk) {
        GroupApplication groupApplication = groupApplicationRepository.findById(groupApplicationPk).orElseThrow();
        Member host = groupApplication.getHost();
        Member guest = groupApplication.getGuest();
        String hostName = host.getNickname();
        String guestName = guest.getNickname();
        String name = generateGroupName();
        float profitRate = getProfitRate();
        LocalDateTime createdDate = LocalDateTime.now();

        KkanbuGroup kkanbuGroup = KkanbuGroup
                .builder()
                .host(host)
                .guest(guest)
                .hostName(hostName)
                .guestName(guestName)
                .name(name)
                .profitRate(profitRate)
                .createdDate(createdDate)
                .build();

        groupRepository.save(kkanbuGroup);
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
