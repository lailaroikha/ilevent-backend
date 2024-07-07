package com.ilevent.ilevent_backend.price.dto;

import com.ilevent.ilevent_backend.price.entity.Price;
import lombok.Data;

import java.time.Instant;

@Data
public class PriceResponseDto {
    private Long id;
    private Long typeticketId;
    private Double priceBeforeDiscount;
    private Double totalDiscount;
    private Instant createdAt;
    private Instant updateAt;

    public static PriceResponseDto fromEntity(Price price) {
        PriceResponseDto dto = new PriceResponseDto();
        dto.setId(price.getId());
        dto.setTypeticketId(price.getTypeticketId().getId());
        dto.setPriceBeforeDiscount(price.getPriceBeforeDiscount());
        dto.setTotalDiscount(price.getTotalDiscount());
        dto.setCreatedAt(price.getCreatedAt());
        dto.setUpdateAt(price.getUpdateAt());
        return dto;
    }
}
