package com.bull4jo.kkanbustock.group.controller.dto;

import com.bull4jo.kkanbustock.group.domain.entity.GroupApplication;
import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReceivedGroupApplicationListResponse {
    private final KkanbuGroupPK groupApplicationPk;
    private final String hostName;

    @Builder
    public ReceivedGroupApplicationListResponse(GroupApplication groupApplication) {
        this.groupApplicationPk = groupApplication.getGroupApplicationPk();
        this.hostName = groupApplication.getHostName();
    }
}