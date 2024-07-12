package com.ilevent.ilevent_backend.promoReferral.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class PromoReferralRequestDto {
    private Long eventsId;
    private Long usersID;
    private Instant start;
    private Instant end;
    private LocalDate expired;
    private Integer maxClaims;
}
