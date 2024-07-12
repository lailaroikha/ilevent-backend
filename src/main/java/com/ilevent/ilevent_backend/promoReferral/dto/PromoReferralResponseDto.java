package com.ilevent.ilevent_backend.promoReferral.dto;

import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class PromoReferralResponseDto {
    private Long id;
    private Long eventsId;
    private Long usersID;
    private Integer promoValueDiscount;
    private Instant start;
    private Instant end;
    private LocalDate expired;
    private Integer used;
    private Integer maxClaims;

    public static PromoReferralResponseDto fromEntity(PromoReferral promoReferral) {
        PromoReferralResponseDto dto = new PromoReferralResponseDto();
        dto.setId(promoReferral.getId());
        dto.setEventsId(promoReferral.getEventsId().getId());
        dto.setUsersID(promoReferral.getUsersID().getId());
        dto.setPromoValueDiscount(promoReferral.getPromoValueDiscount());
        dto.setStart(promoReferral.getStart());
        dto.setEnd(promoReferral.getEnd());
        dto.setExpired(promoReferral.getExpired());
        dto.setUsed(promoReferral.getUsed());
        dto.setMaxClaims(promoReferral.getMaxClaims());
        return dto;
    }
}