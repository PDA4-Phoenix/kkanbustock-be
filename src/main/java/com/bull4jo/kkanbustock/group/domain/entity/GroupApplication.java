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
public class GroupApplication {

    @EmbeddedId
    private KkanbuGroupPK groupApplicationPk;

    @ManyToOne
    @MapsId("hostId")
    private Member host;

    @ManyToOne
    @MapsId("guestId")
    private Member guest;

    @Column(nullable = false)
    private String hostName;

    @Column
    private boolean approvalStatus;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public GroupApplication(KkanbuGroupPK groupApplicationPk, Member host, Member guest, String hostName, boolean approvalStatus, LocalDateTime createdDate) {
        this.groupApplicationPk = groupApplicationPk;
        this.host = host;
        this.guest = guest;
        this.hostName = hostName;
        this.approvalStatus = approvalStatus;
        this.createdDate = createdDate;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
