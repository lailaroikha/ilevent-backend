package com.ilevent.ilevent_backend.referral.controller;

import com.ilevent.ilevent_backend.referral.dto.ReferralResponseDto;
import com.ilevent.ilevent_backend.referral.dto.ReferralRequestDto;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.service.ReferralService;
import com.ilevent.ilevent_backend.users.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/referral")
public class ReferralController {
    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ReferralResponseDto> applyReferralCode(@RequestBody ReferralRequestDto referralRequestDto) {
        ReferralResponseDto response = referralService.applyReferralCode(referralRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReferralResponseDto> getReferralDetails(@PathVariable Long userId) {
        Referral referral = referralService.getReferralDetails(userId);
        ReferralResponseDto response = ReferralResponseDto.fromEntity(referral);
        return ResponseEntity.ok(response);
    }
}
