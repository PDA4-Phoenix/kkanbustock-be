package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReceivedGroupApplicationListRequest {
    private final String guestId;
    private final boolean approvalStatus;
}
