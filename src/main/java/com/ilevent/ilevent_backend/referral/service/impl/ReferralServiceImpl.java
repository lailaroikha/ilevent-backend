package com.ilevent.ilevent_backend.referral.service.impl;

import com.ilevent.ilevent_backend.referral.dto.ReferralResponseDto;
import com.ilevent.ilevent_backend.referral.dto.ReferralRequestDto;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.referral.service.ReferralService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;

    public ReferralServiceImpl(ReferralRepository referralRepository, UserRepository userRepository) {
        this.referralRepository = referralRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ReferralResponseDto applyReferralCode(ReferralRequestDto dto) {
        //Validate the referral code
        Optional<Users> referringUser = userRepository.findByReferralCode(dto.getReferralCode());
        if (referringUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid referral code");
        }

        // Allocate points to the referring user
        Users referrer = referringUser.get();
        referrer.setPoints(referrer.getPoints() + 10000);
        userRepository.save(referrer);

        // Retrieve the new user
        Optional<Users> newUser = userRepository.findById(dto.getUserId());
        if (newUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        // Save the referral details
        Referral referral = new Referral();
        referral.setUserId(referrer);
        referral.setReferredUserId(newUser.get());
        referral.setPoints(10000);
        referral.setCreatedAt(Instant.now());
        referral.setExpiredAt(LocalDate.now().plusMonths(3));
        referralRepository.save(referral);
        // Prepare the response using fromEntity method
        return ReferralResponseDto.fromEntity(referral);
    }

    @Override
    public Referral getReferralDetails(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<Referral> referral = referralRepository.findByUserId(userId);
        return referral.orElseThrow(() -> new IllegalArgumentException("No referral details found for user"));
    }

    @Override
    public String generateReferralCode(Users users) {
        // Generate a unique referral code
        String referralCode;
        do {
            referralCode = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        } while (userRepository.existsByReferralCode(referralCode));

        users.setReferralCode(referralCode);
        userRepository.save(users);

        return referralCode;
    }
}
