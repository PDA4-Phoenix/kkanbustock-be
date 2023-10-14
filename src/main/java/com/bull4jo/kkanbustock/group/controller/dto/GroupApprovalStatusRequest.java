package com.bull4jo.kkanbustock.group.controller.dto;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GroupApprovalStatusRequest {
    private final KkanbuGroupPK groupApplicationPk;
    private final boolean approvalStatus;
}