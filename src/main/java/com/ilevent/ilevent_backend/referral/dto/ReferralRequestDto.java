package com.ilevent.ilevent_backend.referral.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReferralRequestDto {
    private Long Id;
    private Long userId;
    private String referralCode;
}
