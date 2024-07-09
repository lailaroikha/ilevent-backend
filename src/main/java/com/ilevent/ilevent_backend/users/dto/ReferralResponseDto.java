package com.ilevent.ilevent_backend.users.dto;

import com.ilevent.ilevent_backend.users.entity.Users;
import lombok.Data;

@Data
public class ReferralResponseDto {
    private String referralCode;

    public static ReferralResponseDto fromEntity(Users savedUser) {
        ReferralResponseDto response = new ReferralResponseDto();
        response.setReferralCode(savedUser.getReferralCode());
        return response;
    }
}
