package com.bull4jo.kkanbustock.group.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KkanbuGroupPK implements Serializable {
    private String hostId;
    private String guestId;
}
