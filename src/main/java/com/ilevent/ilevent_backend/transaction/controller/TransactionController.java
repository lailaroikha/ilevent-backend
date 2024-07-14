package com.ilevent.ilevent_backend.transaction.controller;

import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationRequestDto;
import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationResponseDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionRequestDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionResponseDto;
import com.ilevent.ilevent_backend.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
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
        PriceCalculationResponseDto response = transactionService.calculatePrice(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<TransactionResponseDto> submitTransaction(@RequestBody TransactionRequestDto request) {
        TransactionResponseDto response = transactionService.submitTransaction(request);
        return ResponseEntity.ok(response);
    }
}
