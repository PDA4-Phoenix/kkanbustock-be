package com.bull4jo.kkanbustock.group.controller.dto;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReceivedGroupApplicationListResponse {
    private final KkanbuGroupPK groupApplicationPK;
    private final String hostName;
    private final boolean approvalStatus;

    @Builder
    public ReceivedGroupApplicationListResponse(GroupApplication groupApplication) {
        this.groupApplicationPK = groupApplication.getGroupApplicationPK();
        this.hostName = groupApplication.getHostName();
        this.approvalStatus = groupApplication.isApprovalStatus();
    }
}