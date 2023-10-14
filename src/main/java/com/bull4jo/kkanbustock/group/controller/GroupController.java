package com.bull4jo.kkanbustock.group.controller;

import com.bull4jo.kkanbustock.group.controller.dto.*;
import com.bull4jo.kkanbustock.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("v1/groups/application")
    public ResponseEntity<List<ReceivedGroupApplicationListResponse>> getReceivedGroupApplications(@RequestBody ReceivedGroupApplicationListRequest receivedGroupApplicationListRequest) {
        return ResponseEntity
                .ok(groupService.getReceivedGroupApplications(receivedGroupApplicationListRequest));
    }

    @GetMapping("/v1/groups")
    public ResponseEntity<List<GroupResponse>> getMyGroups(@RequestBody GroupRequest groupRequest) {
        return ResponseEntity
                .ok(groupService.getMyGroups(groupRequest));
    }

//    @GetMapping("/v1/groups")
//    public ResponseEntity<List<GroupResponse>> getGroups() {
//        return ResponseEntity
//                .ok(groupService.getGroups());
//    }

    @GetMapping("/v1/groups/{groupId}")
    public ResponseEntity<GroupResponse> getGroup(
            @PathVariable(value = "groupId") Long groupId
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
}
