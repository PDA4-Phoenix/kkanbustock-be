package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
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
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_APPLICATION_NOT_FOUND));
        return receivedGroupApplications
                .stream()
                .map(ReceivedGroupApplicationListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getMyGroups(final String memberId) {
        List<KkanbuGroup> myGroups = groupRepository
                .findAllByHostIdOrGuestId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MY_GROUP_NOT_FOUND));

        return myGroups
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

    @Transactional
    public void applyGroup(GroupApplicationRequest groupApplicationRequest) {
        String email = groupApplicationRequest.getEmail();
        String hostId = groupApplicationRequest.getHostId();
        LocalDateTime createdDate = LocalDateTime.now();

        Member guest = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Member host = memberRepository.findById(hostId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
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
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_APPLICATION_NOT_FOUND));

        boolean approvalStatus = groupApplication.isApprovalStatus();

        if (!approvalStatus) {
            Member host = groupApplication.getHost();
            Member guest = groupApplication.getGuest();
            String hostName = host.getNickname();
            String guestName = guest.getNickname();
            String name = groupNameGenerator.generateGroupName();
            float profitRate = getGroupProfitRate(host.getId(), guest.getId());
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

    public float getGroupProfitRate(String hostId, String guestId) {
        KkanbuGroupPK kkanbuGroupPK = new KkanbuGroupPK(hostId, guestId);

        Float hostTotalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getHostId());
        Float hostTotalPurchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getHostId());

        Float guestTotalEquities = portfolioRepository.calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getGuestId());
        Float guestTotalPurchaseAmount = portfolioRepository.calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getGuestId());

        return ((hostTotalEquities + guestTotalEquities) / (hostTotalPurchaseAmount + guestTotalPurchaseAmount) - 1) * 100;
    }
}
