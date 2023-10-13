package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GroupNameRequest {
    private final String name;
    private final Long hostId;
}
