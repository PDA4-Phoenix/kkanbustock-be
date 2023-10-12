package com.bull4jo.kkanbustock.group.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GroupRequest {
    private final GroupNameRequest groupNameRequest;
    private final GroupCodeGenerationRequest groupCodeGenerationRequest;
    private final GroupCodeRetrievalRequest groupCodeRetrievalRequest;
    private final JoinGroupRequest joinGroupRequest;
}
