package com.bull4jo.kkanbustock.group.controller;

import com.bull4jo.kkanbustock.group.controller.dto.*;
import com.bull4jo.kkanbustock.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("v1/groups/application")
    public ResponseEntity<List<ReceivedGroupApplicationListResponse>> getReceivedGroupApplications(@RequestParam final String guestId, @RequestParam final boolean approvalStatus) {
        return ResponseEntity
                .ok(groupService.getReceivedGroupApplications(guestId, approvalStatus));
    }

    @GetMapping("/v1/groups/{memberId}")
    public ResponseEntity<List<GroupResponse>> getMyGroups(@PathVariable String memberId) {
        return ResponseEntity
                .ok(groupService.getMyGroups(memberId));
    }

//    @GetMapping("/v1/groups")
//    public ResponseEntity<List<GroupResponse>> getGroups() {
//        return ResponseEntity
//                .ok(groupService.getGroups());
//    }

    @GetMapping("/v1/groups/{groupId}")
    public ResponseEntity<GroupResponse> getGroup(
            @PathVariable(value = "groupId") KkanbuGroupPK groupId
    ) {
        return ResponseEntity
                .ok(groupService.getGroup(groupId));
    }

    @PostMapping("/v1/groups/application")
    public void applyGroup(@RequestBody GroupApplicationRequest groupApplicationRequest) {
        groupService.applyGroup(groupApplicationRequest);
    }

    @PostMapping("/v1/groups")
    public void changeApprovalStatus(@RequestBody GroupApprovalStatusRequest groupApprovalStatusRequest) {
        groupService.changeApprovalStatus(groupApprovalStatusRequest);
    }

    @GetMapping("/v1/groups/profits")
    public float getGroupProfitRate(@RequestParam KkanbuGroupPK kkanbuGroupPK) {
        return groupService.getGroupProfitRate(kkanbuGroupPK);
    }
}
