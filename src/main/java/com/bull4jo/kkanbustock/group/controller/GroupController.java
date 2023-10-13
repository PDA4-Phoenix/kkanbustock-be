package com.bull4jo.kkanbustock.group.controller;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroupPK;
import com.bull4jo.kkanbustock.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/v1/groups/profits")
    public float getGroupProfitRate(@RequestParam KkanbuGroupPK kkanbuGroupPK) {
        return groupService.getGroupProfitRate(kkanbuGroupPK);
    }


}
