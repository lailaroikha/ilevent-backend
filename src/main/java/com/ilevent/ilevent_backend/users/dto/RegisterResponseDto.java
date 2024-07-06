package com.ilevent.ilevent_backend.users.dto;

import com.ilevent.ilevent_backend.users.entity.Users;
import lombok.Data;

@Data
public class RegisterResponseDto {
    private String name;
    private String email;
    private Boolean isOrganizer;
    private String referralCode;
    private String username;

    public static RegisterResponseDto fromEntity(Users savedUser) {
        RegisterResponseDto dto = new RegisterResponseDto();
        dto.setName(savedUser.getName());
        dto.setEmail(savedUser.getEmail());
        dto.setIsOrganizer(savedUser.getIsOrganizer());
        dto.setReferralCode(savedUser.getReferralCode());
        dto.setUsername(savedUser.getUsername());
        return dto;
    }
}
