package com.ilevent.ilevent_backend.price.dto;

import lombok.Data;

@Data
public class PriceRequestDto {
    private Long typeticketId;
    private Double priceBeforeDiscount;
    private Double totalDiscount;
}
