package com.ilevent.ilevent_backend.price.controller;

import com.ilevent.ilevent_backend.price.dto.PriceRequestDto;
import com.ilevent.ilevent_backend.price.dto.PriceResponseDto;
import com.ilevent.ilevent_backend.price.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/prices")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/create")
    public ResponseEntity<PriceResponseDto> createPrice(@RequestBody PriceRequestDto dto) {
        PriceResponseDto response = priceService.createPrice(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceResponseDto> getPriceById(@PathVariable Long id) {
        PriceResponseDto response = priceService.getPriceById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calculate")
    public Double calculatePriceAfterDiscount(@RequestParam Double priceBeforeDiscount, @RequestParam Double totalDiscount) {
        return priceService.calculatePriceAfterDiscount(priceBeforeDiscount, totalDiscount);
    }
}
