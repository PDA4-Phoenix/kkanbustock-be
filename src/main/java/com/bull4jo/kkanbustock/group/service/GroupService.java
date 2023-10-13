package com.bull4jo.kkanbustock.group.service;

import com.bull4jo.kkanbustock.group.controller.dto.GroupApplicationRequest;
import com.bull4jo.kkanbustock.group.controller.dto.GroupApprovalStatusRequest;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
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
    private final MemberRepository memberRepository;

    public void applyGroup(GroupApplicationRequest groupApplicationRequest) {
        Long hostId = groupApplicationRequest.getHostId();
        String email = groupApplicationRequest.getEmail();
        Long guestId = getGuestId(email); // null일 때 예외처리 필요
        String name = generateGroupName();
        // createdDate

        Member host = memberRepository.findById(hostId).orElseThrow();
        Member guest = memberRepository.findById(guestId).orElseThrow();

        KkanbuGroup kkanbuGroup = KkanbuGroup
                .builder()
                .host(host)
                .guest(guest)
                .name(name)
                .build();

        groupRepository.save(kkanbuGroup);
    }

    private void changeApprovalStatus(GroupApprovalStatusRequest groupApprovalStatusRequest) {
        String name = groupApprovalStatusRequest.getName();
        String approvalStatus = groupApprovalStatusRequest.getApprovalStatus();

        KkanbuGroup kkanbuGroup = groupRepository.findByName(name).orElseThrow();

        if (Objects.equals(approvalStatus, "승인")) {
            float profitRate = getProfitRate();
            // name으로 찾은 그룹의 approvalStatus, profitRate를 설정하는 방법?
            // 데이터의 무결성을 보장할 수 없는 이슈?
            kkanbuGroup.setApprovalStatus(approvalStatus);
            kkanbuGroup.setProfitRate(profitRate);

            // 엔티티를 업데이트
            groupRepository.save(kkanbuGroup);
        } else {
            // 거절 - 해당 그룹을 삭제하는 로직
            groupRepository.delete(kkanbuGroup);
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

//    private boolean isEmailExists(String email) {
//        Optional<Member> existingEmail = Optional.ofNullable(memberRepository.findByEmail(email));
//        return existingEmail.isPresent(); // 값이 있다면 true
//    }

    private boolean isGroupNameExists(String name) {
        Optional<KkanbuGroup> existingGroupName = groupRepository.findByName(name);
        return existingGroupName.isPresent(); // 값이 있다면 true
    }
}
