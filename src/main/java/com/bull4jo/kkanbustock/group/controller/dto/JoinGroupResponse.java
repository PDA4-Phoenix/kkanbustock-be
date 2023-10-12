package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class JoinGroupResponse {
    private final String name;
}
