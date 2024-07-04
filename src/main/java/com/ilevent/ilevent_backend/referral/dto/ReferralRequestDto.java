package com.ilevent.ilevent_backend.referral.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReferralRequestDto {
    @NotBlank
    private String referralCode;

    @NotNull
    private Long userId;
}
