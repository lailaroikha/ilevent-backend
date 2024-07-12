//package com.ilevent.ilevent_backend.promoReferral.service.impl;
//
//import com.ilevent.ilevent_backend.events.entity.Events;
//import com.ilevent.ilevent_backend.events.repository.EventRepository;
//import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
//import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
//import com.ilevent.ilevent_backend.promoReferral.service.PromoReferralService;
//import com.ilevent.ilevent_backend.users.entity.Users;
//import com.ilevent.ilevent_backend.users.repository.UserRepository;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.stereotype.Service;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.Optional;
//
//@Service
//public class PromoReferralServiceImpl implements PromoReferralService {
//    private final PromoReferralRepository promoReferralRepository;
//    private final EventRepository eventRepository;
//    private final UserRepository userRepository;
//
//    public PromoReferralServiceImpl(PromoReferralRepository promoReferralRepository, EventRepository eventRepository, UserRepository userRepository) {
//        this.promoReferralRepository = promoReferralRepository;
//        this.eventRepository = eventRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    @Override
//    public PromoReferral createPromoReferral(Long eventId, Long userId, Integer promoValueDiscount, Integer maxClaims, Instant start, Instant end) {
//        Optional<Events> eventOpt = eventRepository.findById(eventId);
//        if (eventOpt.isEmpty()) {
//            throw new IllegalArgumentException("Event not found");
//        }
//        Events event = eventOpt.get();
//
//        Optional<Users> userOpt = userRepository.findById(userId);
//        if (userOpt.isEmpty()) {
//            throw new IllegalArgumentException("User not found");
//        }
//        Users user = userOpt.get();
//
//        PromoReferral promoReferral = new PromoReferral();
//        promoReferral.setEventsId(event);
//        promoReferral.setUsersID(user);
//        promoReferral.setPromoValueDiscount(promoValueDiscount);
//        promoReferral.setMaxClaims(maxClaims);
//        promoReferral.setStart(start);
//        promoReferral.setEnd(end);
//        promoReferral.setExpired(LocalDate.from(start.plus(3, ChronoUnit.MONTHS)));
//        promoReferral.setUsed(0);
//        promoReferral.setCreatedAt(Instant.now());
//        promoReferral.setUpdateAt(Instant.now());
//
//        return promoReferralRepository.save(promoReferral);
//    }
//
//    @Override
//    public boolean canUseReferral(Long eventId, Long userId) {
//        Optional<PromoReferral> promoReferralOpt = promoReferralRepository.findByEventsIdAndUsersID(eventId, userId);
//        return promoReferralOpt.map(promoReferral -> promoReferral.getUsed() < promoReferral.getMaxClaims()).orElse(false);
//    }
//
//    @Transactional
//    @Override
//    public void incrementUsedReferral(Long eventId, Long userId) {
//        Optional<PromoReferral> promoReferralOpt = promoReferralRepository.findByEventsIdAndUsersID(eventId, userId);
//        if (promoReferralOpt.isPresent()) {
//            PromoReferral promoReferral = promoReferralOpt.get();
//            promoReferral.setUsed(promoReferral.getUsed() + 1);
//            promoReferralRepository.save(promoReferral);
//        } else {
//            throw new IllegalArgumentException("No promo referral found for the event and user");
//        }
//    }
//}