package com.ilevent.ilevent_backend.voucher.controller;


import com.ilevent.ilevent_backend.voucher.dto.VoucherRequestDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilevent.ilevent_backend.voucher.service.VoucherService;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/create")
    public ResponseEntity<VoucherResponseDto> createVoucher(@RequestBody VoucherRequestDto dto) {
        VoucherResponseDto response = voucherService.createVoucher(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<VoucherResponseDto>> getVouchersByUserId(@PathVariable Long userId) {
//        List<VoucherResponseDto> vouchers = voucherService.getVouchersByUserId(userId);
//        return ResponseEntity.ok(vouchers);
//    }
}
