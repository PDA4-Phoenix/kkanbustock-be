package com.bull4jo.kkanbustock.group.controller.dto;


import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TopNGroupResponse {

    private final String name; //그룹 명
    private final String hostName;
    private final String guestName;
    private final float profitRate; //그룹 수익률


    @Builder
    public TopNGroupResponse(KkanbuGroup kkanbuGroup){
        this.name = kkanbuGroup.getName();
        this.hostName = kkanbuGroup.getHostName();
        this.guestName = kkanbuGroup.getGuestName();
        this.profitRate = kkanbuGroup.getProfitRate();
    }

}
