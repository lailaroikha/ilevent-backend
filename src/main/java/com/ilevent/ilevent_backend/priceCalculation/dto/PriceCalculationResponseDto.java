package com.ilevent.ilevent_backend.priceCalculation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceCalculationResponseDto {
    private Double totalAmount;
    private Double amountAfterDiscount;
    private Double pointsDiscount;
}
