package com.ilevent.ilevent_backend.referral.service;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.users.entity.Users;

public interface ReferralService {
    Referral applyReferral(Users User, String referralCode);
    Referral getReferral(Users user);
    String generateReferralCode(Users users);

}
