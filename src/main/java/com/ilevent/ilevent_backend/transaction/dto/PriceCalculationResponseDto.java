package com.ilevent.ilevent_backend.transaction.dto;

import lombok.Data;

@Data
public class PriceCalculationResponseDto {
    private Double totalAmount;
    private Double amountAfterDiscount;
    private Double pointsDiscount;
    private boolean isPointsUsed;
}