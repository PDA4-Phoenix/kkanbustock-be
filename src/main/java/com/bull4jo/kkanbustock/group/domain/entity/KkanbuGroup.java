package com.bull4jo.kkanbustock.group.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public class KkanbuGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column
    private Long hostId;

    @Column
    private Long guestId;

    @Column
    private float profitRate;

    @Column(length = 5)
    private String inviteCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdDate;
}
