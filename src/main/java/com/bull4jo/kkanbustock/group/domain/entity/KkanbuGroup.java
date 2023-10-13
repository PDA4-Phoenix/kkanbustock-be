package com.bull4jo.kkanbustock.group.domain.entity;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public class KkanbuGroup {
    @EmbeddedId
    private KkanbuGroupPK kkanbuGroupPK;

    @ManyToOne
    @MapsId("hostId")
    private Member host;

    @ManyToOne
    @MapsId("guestId")
    private Member guest;

    @Column(columnDefinition = "VARCHAR(2) DEFAULT '대기'")
    private String approvalStatus; // 대기, 승인, 거절

    @Column
    private float profitRate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public KkanbuGroup(KkanbuGroupPK kkanbuGroupPK, Member host, Member guest, String approvalStatus, float profitRate, String name, LocalDateTime createdDate) {
        this.kkanbuGroupPK = kkanbuGroupPK;
        this.host = host;
        this.guest = guest;
        this.approvalStatus = approvalStatus;
        this.profitRate = profitRate;
        this.name = name;
        this.createdDate = createdDate;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setProfitRate(float profitRate) {
        this.profitRate = profitRate;
    }
}
