package com.bull4jo.kkanbustock.group.controller.dto;


import lombok.Getter;

@Getter
public class MyGroupProfitRateResponse {

    private final String memberId; // Member 클래스의 id
    private final String name; //그룹 명
    private final float profitRate; // 내가 속한 그룹 수익률

    public MyGroupProfitRateResponse(String name, float profitRate, String memberId) {
        this.name = name;
        this.profitRate = profitRate;
        this.memberId = memberId;
    }

}