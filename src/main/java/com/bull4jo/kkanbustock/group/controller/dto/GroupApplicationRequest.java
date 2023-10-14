package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GroupApplicationRequest {
    private final Long hostId;
    private final String email;
}
