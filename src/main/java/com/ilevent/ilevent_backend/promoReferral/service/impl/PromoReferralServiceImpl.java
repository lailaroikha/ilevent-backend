package com.ilevent.ilevent_backend.promoReferral.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralRequestDto;
import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralResponseDto;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
import com.ilevent.ilevent_backend.promoReferral.service.PromoReferralService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class PromoReferralServiceImpl implements PromoReferralService {
    private final PromoReferralRepository promoReferralRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public PromoReferralServiceImpl(PromoReferralRepository promoReferralRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.promoReferralRepository = promoReferralRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PromoReferralResponseDto createPromoReferral(PromoReferralRequestDto dto) {
        // Replace with appropriate event ID and user ID from request DTO
        Long eventsId = 1L; // replace with actual event ID from the request
        Long usersId = 1L; // replace with actual user ID from the request

        Optional<Events> eventsOptional = eventRepository.findById(eventsId);
        if (eventsOptional.isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }

        Optional<Users> userOptional = userRepository.findById(usersId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        PromoReferral promoReferral = new PromoReferral();
        promoReferral.setEventsId(eventsOptional.get());
        promoReferral.setUsersId(userOptional.get());
        promoReferral.setPromoValueDiscount(10); // Always 10% discount for referral
        promoReferral.setStart(dto.getStart());
        promoReferral.setEnd(dto.getEnd());
        promoReferral.setMaxClaims(dto.getMaxClaims());
        promoReferral.setUsed(0); // Initialize the used count to 0
        promoReferral.setCreatedAt(Instant.now());
        promoReferral.setUpdateAt(Instant.now());

        PromoReferral savedPromoReferral = promoReferralRepository.save(promoReferral);
        return PromoReferralResponseDto.fromEntity(savedPromoReferral);
    }

    @Override
    public PromoReferralResponseDto getPromoReferralByEventId(Long eventsId) {
        Optional<Events> eventsOptional = eventRepository.findById(eventsId);
        if (eventsOptional.isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }
        Events event = eventsOptional.get();
        List<PromoReferral> promoReferralList = promoReferralRepository.findByEventsId(event);
        if (promoReferralList.isEmpty()) {
            throw new IllegalArgumentException("Promo referral not found for event id: " + eventsId);
        }
        return PromoReferralResponseDto.fromEntity(promoReferralList.get(0));
    }

    @Override
    public void incrementUsedCount(Long promoId) {
        Optional<PromoReferral> promoReferralOptional = promoReferralRepository.findById(promoId);
        if (promoReferralOptional.isPresent()) {
            PromoReferral promoReferral = promoReferralOptional.get();
            if (promoReferral.getUsed() < promoReferral.getMaxClaims()) {
                promoReferral.setUsed(promoReferral.getUsed() + 1);
                promoReferralRepository.save(promoReferral);
            } else {
                throw new IllegalArgumentException("Promo referral usage limit reached");
            }
        } else {
            throw new IllegalArgumentException("Promo referral not found with id: " + promoId);
        }
    }
}
