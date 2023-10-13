package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GroupApprovalStatusRequest {
    private final String name;
    private final String approvalStatus;
}
