package com.ilevent.ilevent_backend.referral.service;

import com.ilevent.ilevent_backend.referral.dto.ReferralResponseDto;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.dto.ReferralRequestDto;
import com.ilevent.ilevent_backend.users.entity.Users;

public interface ReferralService {
    ReferralResponseDto applyReferralCode(ReferralRequestDto referralRequestDTO);
    Referral getReferralDetails(Long userId);
//    String generateReferralCode(Users users);

}
