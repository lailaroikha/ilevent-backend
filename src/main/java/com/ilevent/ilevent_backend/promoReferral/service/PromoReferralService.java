//package com.ilevent.ilevent_backend.promoReferral.service;
//
//import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
//
//import java.time.Instant;
//
//public interface PromoReferralService {
//    PromoReferral createPromoReferral(Long eventId, Long userId, Integer promoValueDiscount, Integer maxClaims, Instant start, Instant end);
//    boolean canUseReferral(Long eventId, Long userId);
//    void incrementUsedReferral(Long eventId, Long userId);
//}
