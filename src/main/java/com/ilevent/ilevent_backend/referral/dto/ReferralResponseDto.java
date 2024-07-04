package com.ilevent.ilevent_backend.referral.dto;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ReferralResponseDto {
    private String referralCode;
    private Long referredUser;
    private Long user;
    private Integer points;

    public static ReferralResponseDto fromEntity(Referral referral) {
        ReferralResponseDto Dto = new ReferralResponseDto();
        Dto.setReferralCode(referral.getReferredUser().getReferralCode());
        Dto.setReferredUser(referral.getReferredUser().getId());
        Dto.setUser(referral.getUser().getId());
        Dto.setPoints(referral.getPoints());
        return Dto;
    }
}
