package com.bull4jo.kkanbustock.group.controller.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyGroupProfitRateRequest {

    private final String memberId;
    private final int n;
}