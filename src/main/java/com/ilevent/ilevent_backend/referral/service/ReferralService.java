package com.ilevent.ilevent_backend.referral.service;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.users.entity.Users;

public interface ReferralService {
    Referral createReferral(Users users);
    void removeReferral(Referral referral);

}
