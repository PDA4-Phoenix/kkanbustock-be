package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.GroupNameRequest;
import com.bull4jo.kkanbustock.group.controller.dto.InviteCodeGenerationResponse;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import com.bull4jo.kkanbustock.group.repository.GroupRepository;
import com.bull4jo.kkanbustock.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final PortfolioRepository portfolioRepository;

    public String setGroupName(GroupNameRequest groupNameRequest) {
        String name = groupNameRequest.getName();
        Long hostId = groupNameRequest.getHostId();

        if (isGroupNameExists(name)) {
            return "중복입니다."; // 그룹 이름이 중복일 경우
        } else {
            return "중복이 아닙니다."; // 그룹 이름이 중복이 아닌 경우
        }
    }

    public InviteCodeGenerationResponse setInviteCode(String name) {
        String inviteCode;

        // 중복이 아닐 때까지 초대코드 생성 반복
        do {
            inviteCode = generateRandomCode();
        } while (isInviteCodeExists(inviteCode));

        // KkanbuGroup 객체 생성 및 저장
        KkanbuGroup kkanbuGroup = KkanbuGroup.builder()
                .name(name)
                .inviteCode(inviteCode)
                .build();

        groupRepository.save(kkanbuGroup);

        // 초대코드 반환
        return InviteCodeGenerationResponse.builder().inviteCode(inviteCode).build();
    }

    private boolean isGroupNameExists(String name) {
        Optional<KkanbuGroup> existingGroupName = groupRepository.findByName(name);
        return existingGroupName.isPresent(); // 값이 있다면 true
    }

    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }

        return code.toString();
    }

    private boolean isInviteCodeExists(String inviteCode) {
        Optional<KkanbuGroup> existingInviteCode = groupRepository.findByInviteCode(inviteCode);
        return existingInviteCode.isPresent(); // 값이 있다면 true
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
