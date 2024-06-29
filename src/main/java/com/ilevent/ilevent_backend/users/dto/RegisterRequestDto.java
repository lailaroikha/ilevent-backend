package com.ilevent.ilevent_backend.users.dto;


import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDto implements Serializable {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank (message = "Email is required")
    private String email;

    @NotBlank (message = "Password is required")
    private String password;

    @NotNull (message ="Role is mandatory")
    private Boolean isOrganizer;

    @Size(max=20, message = "Referral code must be less than 20 character")
    private String referralCode;


    public Users toEntity() {
        Users users =new Users();
        users.setName(name);
        users.setEmail(email);
        users.setPassword(password);
        users.setReferralCode(referralCode);
        users.setIsOrganizer(isOrganizer);
        return users;
    }

}
