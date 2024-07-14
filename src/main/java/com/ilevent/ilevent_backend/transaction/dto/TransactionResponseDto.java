package com.ilevent.ilevent_backend.transaction.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {
    private Long transactionId;
    private Double totalAmount;
    private Double amountAfterDiscount;
    private Double pointsDiscount;
    private String paymentStatus;
}
