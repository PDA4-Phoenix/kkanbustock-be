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

    @Column(nullable = false)
    private String hostName;

    @Column(nullable = false)
    private String guestName;

    @Column
    private float profitRate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdDate;


    public void setProfitRate(float profitRate) {
        this.profitRate = profitRate;
    }
    @Builder
    public KkanbuGroup(KkanbuGroupPK kkanbuGroupPK, Member host, Member guest, String hostName, String guestName, float profitRate, String name, LocalDateTime createdDate) {
        this.kkanbuGroupPK = kkanbuGroupPK;
        this.host = host;
        this.guest = guest;
        this.hostName = hostName;
        this.guestName = guestName;
        this.profitRate = profitRate;
        this.name = name;
        this.createdDate = createdDate;
    }
}
