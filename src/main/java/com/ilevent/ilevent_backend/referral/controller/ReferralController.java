package com.ilevent.ilevent_backend.referral.controller;

import com.ilevent.ilevent_backend.referral.dto.ReferralResponseDto;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.service.ReferralService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/referral")
public class ReferralController {
    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReferralResponseDto> getReferralDetails(@PathVariable Long userId) {
        Referral referral = referralService.getReferralDetails(userId);
        ReferralResponseDto response = ReferralResponseDto.fromEntity(referral);
        return ResponseEntity.ok(response);
    }
}
