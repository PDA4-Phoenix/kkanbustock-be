package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GroupApprovalStatusRequest {
    private final Long groupApplicationPk;
    private final boolean approvalStatus;
}