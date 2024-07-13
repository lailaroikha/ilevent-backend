package com.ilevent.ilevent_backend.promoReferral.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromoReferralRequestDto {
    private LocalDate start;
    private LocalDate end;
    private Integer maxClaims;
}
