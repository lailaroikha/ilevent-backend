package com.ilevent.ilevent_backend.users.dto;

import com.ilevent.ilevent_backend.users.entity.Users;
import lombok.Data;

@Data
public class ProfileResponseDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Boolean organizer;
    private Integer totalPoints;
    private String phone;
    private String picture;
    private String referralCode;

    public static ProfileResponseDto fromEntity(Users user) {
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setOrganizer(user.getOrganizer());
        dto.setTotalPoints(user.getTotalPoints());
        dto.setPhone(user.getPhone());
        dto.setPicture(user.getPicture());
        dto.setReferralCode(user.getReferralCode());
        return dto;
    }
}