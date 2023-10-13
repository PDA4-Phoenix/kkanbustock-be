package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.GroupNameRequest;
import com.bull4jo.kkanbustock.group.controller.dto.InviteCodeGenerationResponse;
import com.bull4jo.kkanbustock.group.controller.dto.InviteCodeRetrievalRequest;
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

    public String getGroupNameByInviteCode(InviteCodeRetrievalRequest inviteCodeRetrievalRequest) {
        String inviteCode = inviteCodeRetrievalRequest.getInviteCode();

        Optional<KkanbuGroup> groupOptional = groupRepository.findByInviteCode(inviteCode);

        if (groupOptional.isPresent()) {
            KkanbuGroup group = groupOptional.get();
            return group.getName(); // 일치하는 그룹명 반환
        } else {
            return "일치하는 그룹을 찾을 수 없습니다."; // 일치하는 inviteCode를 찾을 수 없는 경우 메시지 반환
        }
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
}
