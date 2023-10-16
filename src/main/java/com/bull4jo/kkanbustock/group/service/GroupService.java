package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.*;
import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.GroupNameGenerator;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupApplicationRepository groupApplicationRepository;
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;
    private final GroupNameGenerator groupNameGenerator;

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

    // 승인 대기목록에 추가
    @Transactional
    public void applyGroup(GroupApplicationRequest groupApplicationRequest) {
        String email = groupApplicationRequest.getEmail();
        String hostId = groupApplicationRequest.getHostId();
        LocalDateTime createdDate = LocalDateTime.now();

        Member guest = memberRepository.findByEmail(email).orElseThrow();
        Member host = memberRepository.findById(hostId).orElseThrow();
        String hostName = host.getNickname();

        KkanbuGroupPK groupApplicationPk = KkanbuGroupPK
                .builder()
                .hostId(host.getId())
                .guestId(guest.getId())
                .build();

        GroupApplication groupApplication = GroupApplication
                .builder()
                .groupApplicationPk(groupApplicationPk)
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

        if (!approvalStatus) {
            Member host = groupApplication.getHost();
            Member guest = groupApplication.getGuest();
            String hostName = host.getNickname();
            String guestName = guest.getNickname();
            String name = groupNameGenerator.generateGroupName();
            float profitRate = getGroupProfitRate(kkanbuGroupPK);
            LocalDateTime createdDate = LocalDateTime.now();

            KkanbuGroup kkanbuGroup = KkanbuGroup
                    .builder()
                    .kkanbuGroupPK(kkanbuGroupPK)
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

    public float getGroupProfitRate(KkanbuGroupPK kkanbuGroupPK) {
        Float hostTotalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getHostId());
        Float hostTotalPurchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getHostId());

        Float guestTotalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getGuestId());
        Float guestTotalPurchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getGuestId());

        float groupProfitRate = ((hostTotalEquities + guestTotalEquities) / (hostTotalPurchaseAmount + guestTotalPurchaseAmount) - 1) * 100;
        return groupProfitRate;
    }
}
