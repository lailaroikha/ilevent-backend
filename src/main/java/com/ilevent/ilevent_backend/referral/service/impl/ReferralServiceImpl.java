package com.ilevent.ilevent_backend.referral.service.impl;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.referral.service.ReferralService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;

    public ReferralServiceImpl(ReferralRepository referralRepository, UserRepository userRepository) {
        this.referralRepository = referralRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Referral applyReferral(Users User, String referralCode) {
        //Validate the referral code

        return null;
    }

    @Override
    public Referral getReferral(Users user) {
        return null;
    }

    @Override
    public String generateReferralCode(Users users) {
        return "";
    }
}
