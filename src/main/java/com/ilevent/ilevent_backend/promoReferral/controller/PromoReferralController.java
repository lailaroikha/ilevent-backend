//package com.ilevent.ilevent_backend.promoReferral.controller;
//
//
//import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralRequestDto;
//import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralResponseDto;
//import com.ilevent.ilevent_backend.promoReferral.service.PromoReferralService;
//import com.ilevent.ilevent_backend.users.entity.Users;
//import com.ilevent.ilevent_backend.users.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/promo-referrals")
//public class PromoReferralController {
//    private final PromoReferralService promoReferralService;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public PromoReferralController(PromoReferralService promoReferralService, UserRepository userRepository) {
//        this.promoReferralService = promoReferralService;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<PromoReferralResponseDto> createPromoReferral(@RequestBody PromoReferralRequestDto requestDto) {
//        // Get the currently authenticated user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//
//        // Find the user in the database
//        Optional<Users> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        Users user = userOptional.get();
//
//        // Check if the user has the role ROLE_ORGANIZER
//        if (!user.getOrganizer()) {
//            return ResponseEntity.status(403).build(); // Forbidden
//        }
//
//        // Create the promo referral
//        PromoReferralResponseDto responseDto = promoReferralService.createPromoReferral(requestDto);
//        return ResponseEntity.ok(responseDto);
//    }
//}
