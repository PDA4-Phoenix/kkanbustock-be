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

    @Column(columnDefinition = "VARCHAR(2) DEFAULT '대기'")
    private String approvalStatus; // 대기, 승인, 거절

    @Column(nullable = false)
    private LocalDateTime createdDate;

    // 호스트 이름은 이 테이블에 저장하지 않고 코드 상에서 hostId로 검색해서 띄우는 걸로?

    @Builder
    public GroupApplication(KkanbuGroupPK groupApplicationPk, Member host, Member guest, String approvalStatus, LocalDateTime createdDate) {
        this.groupApplicationPk = groupApplicationPk;
        this.host = host;
        this.guest = guest;
        this.approvalStatus = approvalStatus;
        this.createdDate = createdDate;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
