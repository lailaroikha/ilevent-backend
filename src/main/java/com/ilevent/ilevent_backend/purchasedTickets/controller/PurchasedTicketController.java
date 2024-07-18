package com.ilevent.ilevent_backend.purchasedTickets.controller;

import com.ilevent.ilevent_backend.purchasedTickets.dto.PurchasedTicketsResponseDto;
import com.ilevent.ilevent_backend.purchasedTickets.service.PurchasedTicketService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchased-tickets")
public class PurchasedTicketController {
    private final PurchasedTicketService purchasedTicketsService;
    private final UserRepository userRepository;

    public PurchasedTicketController(PurchasedTicketService purchasedTicketsService, UserRepository userRepository) {
        this.purchasedTicketsService = purchasedTicketsService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<PurchasedTicketsResponseDto>> getPurchasedTickets(@RequestParam(required = false) String filter) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("sub"); // Assuming "sub" claim contains the user's email

        List<PurchasedTicketsResponseDto> response = purchasedTicketsService.getPurchasedTicketsByUserId(email, filter);
        return ResponseEntity.ok(response);
    }
}