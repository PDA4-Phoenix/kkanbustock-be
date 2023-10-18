package com.bull4jo.kkanbustock.group.controller.dto;

import com.bull4jo.kkanbustock.group.domain.entity.KkanbuGroup;
import com.bull4jo.kkanbustock.portfolio.domain.Portfolio;
import com.bull4jo.kkanbustock.portfolio.domain.PortfolioResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class KkanbuGroupResponse {
    List<PortfolioResponse> hostPortfolio;
    List<PortfolioResponse> guestPortfolio;

    public KkanbuGroupResponse(List<Portfolio> hostPortfolio, List<Portfolio> guestPortfolio) {
        this.hostPortfolio = hostPortfolio.stream().map(PortfolioResponse::new).collect(Collectors.toList());
        this.guestPortfolio = guestPortfolio.stream().map(PortfolioResponse::new).collect(Collectors.toList());
    }
}
