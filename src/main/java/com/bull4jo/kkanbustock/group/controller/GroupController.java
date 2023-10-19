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

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("v1/groups/proposal")
    public ResponseEntity<List<ReceivedGroupApplicationListResponse>> getReceivedGroupApplications(@RequestParam final String guestId, @RequestParam final boolean approvalStatus) {
        return ResponseEntity
                .ok(groupService.getReceivedGroupApplications(guestId, approvalStatus));
    }

    @GetMapping("/v1/groups/{memberId}")
    public ResponseEntity<List<GroupResponse>> getMyGroups(@PathVariable String memberId) {
        return ResponseEntity
                .ok(groupService.getMyGroups(memberId));
    }

    @GetMapping("/v1/groups/portfolios")
    public ResponseEntity<KkanbuGroupResponse> getPortfoliosByKkanbuGroupPk(@RequestParam String hostId, @RequestParam String guestId) {
        KkanbuGroupPK kkanbuGroupPK = new KkanbuGroupPK(hostId, guestId);
        return ResponseEntity.ok(groupService.getPortfoliosByKkanbuGroupPk(kkanbuGroupPK));
    }

    @GetMapping("/v1/groups")
    public ResponseEntity<List<GroupResponse>> getGroups() {
        return ResponseEntity
                .ok(groupService.getGroups());
    }

    @PostMapping("/v1/groups/proposal")
    public void applyGroup(@RequestBody GroupApplicationRequest groupApplicationRequest) {
        groupService.applyGroup(groupApplicationRequest);
    }

    @PostMapping("/v1/groups")
    public void createGroup(@RequestBody KkanbuGroupPK kkanbuGroupPK) {
        groupService.createGroup(kkanbuGroupPK);
    }

    @GetMapping("/v1/groups/profits")
    public float getGroupProfitRate(@RequestParam String hostId, @RequestParam String guestId) {
        return groupService.getGroupProfitRate(hostId, guestId);
    }

    @GetMapping("v1/groups/top-n-groups")
    public ResponseEntity<List<TopNGroupResponse>> getTopNGroups(@RequestParam int n) {
        return ResponseEntity.ok(groupService.findTopNGroups(n));
    }

    @GetMapping("v1/groups/my-groups-profit-rate")
    public ResponseEntity<List<MyGroupProfitRateResponse>> getMyGroupsProfitRate( @RequestParam int n, @RequestParam String memberId) {
        return ResponseEntity.ok(groupService.calculateMyGroupsProfitRate(n, memberId));
    }
}
