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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
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
        String hostName = host.getName();

        KkanbuGroupPK groupApplicationPK = KkanbuGroupPK
                .builder()
                .hostId(host.getId())
                .guestId(guest.getId())
                .build();

        if (isDuplicateGroupApplicationPK(groupApplicationPK)) {
            throw new CustomException(ErrorCode.GROUP_APPLICATION_FORBIDDEN);
        }

        GroupApplication groupApplication = GroupApplication
                .builder()
                .groupApplicationPK(groupApplicationPK)
                .host(host)
                .guest(guest)
                .hostName(hostName)
                .createdDate(createdDate)
                .build();

        groupApplicationRepository.save(groupApplication);
    }

    @Transactional
    public void createGroup(KkanbuGroupPK kkanbuGroupPK) {

        if (isDuplicateGroupPK(kkanbuGroupPK)) {
            throw new CustomException(ErrorCode.GROUP_FORBIDDEN);
        }

        GroupApplication groupApplication = groupApplicationRepository
                .findById(kkanbuGroupPK)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_APPLICATION_NOT_FOUND));

        boolean approvalStatus = groupApplication.isApprovalStatus();

        if (!approvalStatus) {
            Member host = groupApplication.getHost();
            Member guest = groupApplication.getGuest();
            String hostName = host.getName();
            String guestName = guest.getName();
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
            groupApplication.setApprovalStatus(true);
        } else {
            throw new CustomException(ErrorCode.GROUP_FORBIDDEN);
            // 신청 거절 기능 추가 시 수정 필요 (현재 대기 or 승인 상태로만 나뉨)
        }
    }

    @Scheduled (cron = "0 3 2 * * *")
    @Transactional
    public void updateGroupProfitRate() {
        List<KkanbuGroup> groups = groupRepository.findAll();
        for (KkanbuGroup group : groups) {
            group.setProfitRate(getGroupProfitRate(group.getHost().getId(), group.getGuest().getId()));
        }

        // 메서드 실행 시작 로그
        System.out.println("updateGroupProfitRate() 메서드가 실행됩니다.");
    }

    public float getGroupProfitRate(String hostId, String guestId) {
        KkanbuGroupPK kkanbuGroupPK = new KkanbuGroupPK(hostId, guestId);

        Float hostTotalEquities = portfolioRepository
                .calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getHostId())
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_EQUITIES_VALUE_AMOUNT));

        Float hostTotalPurchaseAmount = portfolioRepository
                .calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getHostId())
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_TOTAL_PURCHASE_AMOUNT));

        Float guestTotalEquities = portfolioRepository
                .calculateTotalEquitiesValueByMemberId(kkanbuGroupPK.getGuestId())
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_EQUITIES_VALUE_AMOUNT));

        Float guestTotalPurchaseAmount = portfolioRepository
                .calculateTotalPurchaseAmountByMemberId(kkanbuGroupPK.getGuestId())
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_CALCULATE_TOTAL_PURCHASE_AMOUNT));

        return ((hostTotalEquities + guestTotalEquities) / (hostTotalPurchaseAmount + guestTotalPurchaseAmount) - 1) * 100;
    }

    private boolean isDuplicateGroupPK(KkanbuGroupPK kkanbuGroupPK) {
        Optional<KkanbuGroup> kkanbuGroup = groupRepository.findByKkanbuGroupPK(kkanbuGroupPK);
        return kkanbuGroup.isPresent();
    }

    private boolean isDuplicateGroupApplicationPK(KkanbuGroupPK groupApplicationPK) {

        // host - guest 관계
        Optional<GroupApplication> groupApplication1 = groupApplicationRepository.findByGroupApplicationPK(groupApplicationPK);

        // guest - host 관계
        KkanbuGroupPK reversedGroupPK = new KkanbuGroupPK(groupApplicationPK.getGuestId(), groupApplicationPK.getHostId());
        Optional<GroupApplication> groupApplication2 = groupApplicationRepository.findByGroupApplicationPK(reversedGroupPK);

        return groupApplication1.isPresent() || groupApplication2.isPresent();
    }

    @Transactional(readOnly = true)
    public List<TopNGroupResponse> findTopNGroups(int n) {
        // findTopByKkanbuGroupPKOrderByProfitRateDesc 메서드를 호출하여 전체 그룹을 조회
        List<KkanbuGroup> groupList = groupRepository.findTopByOrderByProfitRateDesc()
                .orElseThrow(() -> new CustomException(ErrorCode.TOP_GROUP_NOT_FOUND));

        if (groupList.size() < n) {
            return groupList.stream()
                    .map(TopNGroupResponse::new)
                    .collect(Collectors.toList());
        } else {
            return groupList.subList(0, n).stream()
                    .map(TopNGroupResponse::new)
                    .collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public List<MyGroupProfitRateResponse> calculateMyGroupsProfitRate(int n, String memberId) {
        List<KkanbuGroup> myGroups = groupRepository
                .findAllByHostIdOrGuestId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MY_GROUP_NOT_FOUND));

        // 수익률로 내림차순 정렬
        List<KkanbuGroup> sortedGroups = myGroups.stream()
                .sorted(Comparator.comparing(KkanbuGroup::getProfitRate).reversed())
                .collect(Collectors.toList());

        // 상위 n개의 그룹만 선택하고 MyGroupProfitRateResponse로 매핑
        return sortedGroups.stream()
                .limit(n)
                .map(group -> new MyGroupProfitRateResponse(group.getName(), group.getProfitRate(), memberId))
                .collect(Collectors.toList());
    }



}