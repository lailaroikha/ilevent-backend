package com.ilevent.ilevent_backend.referral.service.impl;

import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.pointsHistory.repository.PointHistoryRepository;
import com.ilevent.ilevent_backend.referral.dto.ReferralResponseDto;
import com.ilevent.ilevent_backend.referral.dto.ReferralRequestDto;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.referral.service.ReferralService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public ReferralServiceImpl(ReferralRepository referralRepository, UserRepository userRepository, PointHistoryRepository pointHistoryRepository) {
        this.referralRepository = referralRepository;
        this.userRepository = userRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }


    @Override
    public ReferralResponseDto applyReferralCode(ReferralRequestDto dto) {

        // Retrieve the new user
        Optional<Users> newUserOpt = userRepository.findById(dto.getUserId());
        if (newUserOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Users newUser = newUserOpt.get();

        //Check if referral code is provide
        if (dto.getReferralCode() != null && !dto.getReferralCode().isEmpty()) {// Validate the referral code
            Optional<Users> referringUserOpt = userRepository.findByReferralCode(dto.getReferralCode());
            if (referringUserOpt.isEmpty()) {
                throw new IllegalArgumentException("Invalid referral code");
            }
            Users referringUser = referringUserOpt.get();

            // Save the referral details
            Referral referral = new Referral();
            referral.setUser(referringUser);
            referral.setReferredUserId(newUser);
            referral.setCreatedAt(Instant.now());
            referral.setUpdatedAt(Instant.now());
            referralRepository.save(referral);

            // Add points to referrer
            PointsHistory pointsHistory = new PointsHistory();
            pointsHistory.setUser(referringUser);
            pointsHistory.setPoints(10000);
            pointsHistory.setType("REFERRAL");
            pointsHistory.setCreatedAt(Instant.now());
            pointsHistory.setUpdateAt(Instant.now());
            pointHistoryRepository.save(pointsHistory);
            // Prepare the response using fromEntity method
            return ReferralResponseDto.fromEntity(referral);
        } else {
            return  null;
        }
    }

    @Override
    public Referral getReferralDetails(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<Referral> referral = Optional.ofNullable(referralRepository.findReferralByUserId(userId));
        return referral.orElseThrow(() -> new IllegalArgumentException("No referral details found for user"));
    }

}
