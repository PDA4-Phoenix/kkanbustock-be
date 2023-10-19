package com.bull4jo.kkanbustock.group.controller.dto;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupResponse {
    private final String name;
    private final String hostName;
    private final String guestName;
    private final String guestId;
    private final float profitRate;

    @Builder
    public GroupResponse(KkanbuGroup kkanbuGroup) {
        this.name = kkanbuGroup.getName();
        this.hostName = kkanbuGroup.getHostName();
        this.guestName = kkanbuGroup.getGuestName();
        this.guestId = kkanbuGroup.getGuest().getId();
        this.profitRate = kkanbuGroup.getProfitRate();
    }
}
