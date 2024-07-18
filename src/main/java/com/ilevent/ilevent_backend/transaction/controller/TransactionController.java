package com.ilevent.ilevent_backend.transaction.controller;

import com.ilevent.ilevent_backend.transaction.dto.PriceCalculationRequestDto;

import com.ilevent.ilevent_backend.transaction.dto.PriceCalculationResponseDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionRequestDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionResponseDto;
import com.ilevent.ilevent_backend.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/calculate-price")
    public ResponseEntity<PriceCalculationResponseDto> calculatePrice(@RequestBody PriceCalculationRequestDto request) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("sub"); // Assuming "sub" claim contains the user's email

        PriceCalculationResponseDto response = transactionService.calculatePrice(request, email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("sub"); // Assuming "sub" claim contains the user's email

        TransactionResponseDto responseDto = transactionService.submitTransaction(transactionRequestDto, email);
        return ResponseEntity.ok(responseDto);
    }
}
//    @PostMapping("/calculate-price")
//
//    public ResponseEntity<PriceCalculationResponseDto> calculatePrice(@RequestBody PriceCalculationRequestDto request) {
//
//        PriceCalculationResponseDto response = transactionService.calculatePrice(request);
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/submit")
//    public ResponseEntity<TransactionResponseDto> submitTransaction(@RequestBody TransactionRequestDto request) {
//        TransactionResponseDto response = transactionService.submitTransaction(request);
//        return ResponseEntity.ok(response);
//    }
//}
