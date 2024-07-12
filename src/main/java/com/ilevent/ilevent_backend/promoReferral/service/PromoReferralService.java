package com.ilevent.ilevent_backend.promoReferral.service;

import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralRequestDto;
import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralResponseDto;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;

import java.time.Instant;

public interface PromoReferralService {
    PromoReferralResponseDto createPromoReferral(PromoReferralRequestDto dto);
    PromoReferralResponseDto getPromoReferralByEventId(Long eventsId);
    void incrementUsedCount(Long promoId);
}
