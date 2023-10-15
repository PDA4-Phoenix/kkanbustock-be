package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.*;
import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import com.bull4jo.kkanbustock.group.repository.GroupApplicationRepository;
import com.bull4jo.kkanbustock.group.repository.GroupRepository;
import com.bull4jo.kkanbustock.member.domain.entity.Member;
import com.bull4jo.kkanbustock.member.repository.MemberRepository;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = true)
    public List<ReceivedGroupApplicationListResponse> getReceivedGroupApplications(final String guestId, final boolean approvalStatus) {
        List<GroupApplication> receivedGroupApplications = groupApplicationRepository
                .findByGuestIdAndApprovalStatus(guestId, approvalStatus)
                .orElseThrow();
        return receivedGroupApplications
                .stream()
                .map(ReceivedGroupApplicationListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getMyGroups(final String memberId) {
        List<KkanbuGroup> allGroups = groupRepository
                .findAllByHostIdOrGuestId(memberId)
                .orElseThrow();

        return allGroups
                .stream()
                .map(GroupResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getGroups() {
        return groupRepository
                .findAll()
                .stream()
                .map(GroupResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupResponse getGroup(String hostId, String guestId) {
        KkanbuGroupPK kkanbuGroupPK = new KkanbuGroupPK(hostId, guestId);
        KkanbuGroup kkanbuGroup = groupRepository
                .findById(kkanbuGroupPK)
                .orElseThrow();
        return new GroupResponse(kkanbuGroup);
    }

    // 승인 대기목록에 추가
    @Transactional
    public void applyGroup(GroupApplicationRequest groupApplicationRequest) {
        String email = groupApplicationRequest.getEmail();
        String guestId = getGuestId(email);

        String hostId = groupApplicationRequest.getHostId();
        LocalDateTime createdDate = LocalDateTime.now();

        Member host = memberRepository.findById(hostId).orElseThrow();
        Member guest = memberRepository.findById(guestId).orElseThrow();
        String hostName = host.getNickname();

        GroupApplication groupApplication = GroupApplication
                .builder()
                .host(host)
                .guest(guest)
                .hostName(hostName)
                .createdDate(createdDate)
                .build();

        groupApplicationRepository.save(groupApplication);
    }

    @Transactional
    public void createGroup(KkanbuGroupPK kkanbuGroupPK) {

        GroupApplication groupApplication = groupApplicationRepository
                .findById(kkanbuGroupPK)
                .orElseThrow();

        boolean approvalStatus = groupApplication.isApprovalStatus();

        if (approvalStatus == false) {
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
        } else {
            throw new RuntimeException("이미 생성된 그룹입니다.");
        }
    }

    private String generateGroupName() {
        // 그룹 이름을 만들어주는 로직
        // 임시로 리턴
        return "test";
    }

    private String getGuestId(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No Member Exists"));
        String memberId = member.getId();

        if (memberId == null) {
            throw new IllegalArgumentException("Invalid guest email: " + email);
        }
        return memberId;
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
    public float getGroupProfitRate(KkanbuGroupPK kkanbuGroupPK) {
        Float hostTotalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getHostId());
        Float hostTotalPurchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getHostId());

        Float guestTotalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getGuestId());
        Float guestTotalPurchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getGuestId());

        float groupProfitRate = ((hostTotalEquities + guestTotalEquities) / (hostTotalPurchaseAmount + guestTotalPurchaseAmount) - 1) * 100;
        return groupProfitRate;
    }
}
