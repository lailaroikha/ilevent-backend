package com.ilevent.ilevent_backend.referral.service.impl;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.pointsHistory.repository.PointHistoryRepository;
//import com.ilevent.ilevent_backend.referral.dto.ReferralResponseDto;
//import com.ilevent.ilevent_backend.referral.dto.ReferralRequestDto;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.referral.service.ReferralService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;
    private final PointHistoryService pointHistoryService;

    public ReferralServiceImpl(ReferralRepository referralRepository, UserRepository userRepository, PointHistoryService pointHistoryService) {
        this.referralRepository = referralRepository;
        this.userRepository = userRepository;
        this.pointHistoryService =pointHistoryService;
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

            pointHistoryService.addPointsHistory(referringUser.getId(), 10000, "REFERRAL");

//            PointsHistory pointsHistory = new PointsHistory();
//            pointsHistory.setUser(referringUser);
//            pointsHistory.setPoints(10000);
//            pointsHistory.setType("REFERRAL");
//            pointHistoryRepository.save(pointsHistory);

            return "Referral code applied successfully";
        } else {
            return "No referral code provided";
        }
    }

}
