package com.ilevent.ilevent_backend.transaction.service;

import com.ilevent.ilevent_backend.transaction.dto.PriceCalculationRequestDto;

import com.ilevent.ilevent_backend.transaction.dto.PriceCalculationResponseDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionRequestDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionResponseDto;


public interface TransactionService {
    PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto priceCalculationRequestDto, String email);
    TransactionResponseDto submitTransaction(TransactionRequestDto transactionRequestDto, String email);
}
