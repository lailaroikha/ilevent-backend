package com.ilevent.ilevent_backend.promoReferral.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromoReferralRequestDto {
    private Long eventsId;
    private Long usersID;
    private LocalDate start;
    private LocalDate end;
//    private LocalDate expired;
    private Integer maxClaims;
}
