package com.ilevent.ilevent_backend.referral.dto;

import com.ilevent.ilevent_backend.referral.entity.Referral;
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
    private String referralCode; // The referral code being applied


    public static ReferralResponseDto fromEntity(Referral referral) {
        ReferralResponseDto dto = new ReferralResponseDto();
        dto.setReferredUserId(referral.getReferredUserId().getId());
        dto.setUserId(referral.getUser().getId());
        dto.setReferralCode(String.valueOf(referral.getReferredUserId().getId())); // The referral code being applied
        return dto;

    }


}
