package com.bull4jo.kkanbustock.group.controller.dto;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class GroupResponse {
    private final GroupCodeGenerationResponse groupCodeGenerationResponse;
    private final JoinGroupResponse joinGroupResponse;
}
