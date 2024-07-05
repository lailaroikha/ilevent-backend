package com.ilevent.ilevent_backend.referral.dto;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.users.entity.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Data
public class ReferralResponseDto {
    private Long referredUserId;
    private Long userId;
    private Integer points;

    public static ReferralResponseDto fromEntity(Referral referral) {
        ReferralResponseDto dto = new ReferralResponseDto();
        dto.setReferredUserId(referral.getReferredUserId().getId());
        dto.setUserId(referral.getUser().getId());
        dto.setPoints(referral.getPoints());
        return dto;
    }
}
