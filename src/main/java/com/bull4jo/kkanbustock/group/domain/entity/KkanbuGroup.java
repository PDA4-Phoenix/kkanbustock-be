package com.bull4jo.kkanbustock.group.domain.entity;

import com.bull4jo.kkanbustock.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public class KkanbuGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "host_id")
    private Member host;

    @OneToOne
    @JoinColumn(name = "guest_id")
    private Member guest;

    @Column
    private float profitRate;

    @Column(length = 5)
    private String inviteCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public KkanbuGroup(Long id, Member host, Member guest, float profitRate, String inviteCode, String name, LocalDateTime createdDate) {
        this.id = id;
        this.host = host;
        this.guest = guest;
        this.profitRate = profitRate;
        this.inviteCode = inviteCode;
        this.name = name;
        this.createdDate = createdDate;
    }
}
