package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.GroupNameRequest;
import com.bull4jo.kkanbustock.group.controller.dto.InviteCodeGenerationResponse;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public void setGroupName(GroupNameRequest groupNameRequest) {
        String name = groupNameRequest.getName();

        KkanbuGroup kkanbuGroup = KkanbuGroup
                .builder()
                .name(name)
                .build();

        groupRepository.save(kkanbuGroup);
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
        // 초대코드 중복 확인
        Optional<KkanbuGroup> existingGroup = groupRepository.findByInviteCode(inviteCode);
        return existingGroup.isPresent(); // 값이 있다면 true
    }
}
