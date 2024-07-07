package com.ilevent.ilevent_backend.price.service;

import com.ilevent.ilevent_backend.price.dto.PriceResponseDto;
import com.ilevent.ilevent_backend.price.dto.PriceRequestDto;


public interface PriceService {
    PriceResponseDto createPrice(PriceRequestDto dto);
    PriceResponseDto getPriceById(Long id);
    void deletePrice(Long id);
    Double calculatePriceAfterDiscount(Double priceBeforeDiscount, Double totalDiscount);
}
