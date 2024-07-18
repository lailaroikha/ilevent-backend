package com.ilevent.ilevent_backend.referral.service.impl;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.referral.service.ReferralService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;
    private final PointHistoryService pointHistoryService;
    private final PromoReferralRepository promoReferralRepository;

    public ReferralServiceImpl(ReferralRepository referralRepository, UserRepository userRepository, PointHistoryService pointHistoryService, PromoReferralRepository promoReferralRepository) {
        this.referralRepository = referralRepository;
        this.userRepository = userRepository;
        this.pointHistoryService =pointHistoryService;
        this.promoReferralRepository = promoReferralRepository;
    }

    @Transactional
    @Override
    public String applyReferralCode(Long userId, String referralCode) {
        Optional<Users> newUserOpt = userRepository.findById(userId);
        if (newUserOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Users newUser = newUserOpt.get();

        if (referralCode != null && !referralCode.isEmpty()) {
            Optional<Users> referringUserOpt = userRepository.findByReferralCode(referralCode);
            if (referringUserOpt.isEmpty()) {
                throw new IllegalArgumentException("Invalid referral code");
            }
            Users referringUser = referringUserOpt.get();

            Referral referral = new Referral();
            referral.setUser(referringUser);
            referral.setReferredUserId(newUser);
            referralRepository.save(referral);
            pointHistoryService.addPointsHistory(referringUser.getId(), 10000, "REFERRAL", LocalDate.now().plusMonths(3));

            // Create promo referral for the referred user
            PromoReferral promoReferral = new PromoReferral();
            promoReferral.setEventsId(null); // need to link it to the specific event if required
            promoReferral.setUsersId(newUser);
            promoReferral.setPromoValueDiscount(10); // 10% discount for referral
            promoReferral.setStart(LocalDate.now());
            promoReferral.setEnd(LocalDate.now().plusMonths(3));
            promoReferral.setMaxClaims(1); // Only the referred user can use it
            promoReferral.setUsed(0);
            promoReferral.setCreatedAt(Instant.now());
            promoReferral.setUpdateAt(Instant.now());
            promoReferralRepository.save(promoReferral);

            // Set referredBy for newUser
            newUser.setPromoReferralUsed(false); // Ensure promoReferralUsed is false for referred users
            userRepository.save(newUser);

            return "Referral code applied successfully";
        } else {
            // Set promoReferralUsed to true if no referral code is provided
            newUser.setPromoReferralUsed(true);
            userRepository.save(newUser);
            return "No referral code provided";
        }
    }

}
