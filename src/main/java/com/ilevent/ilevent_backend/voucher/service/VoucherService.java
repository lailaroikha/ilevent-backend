package com.ilevent.ilevent_backend.voucher.service;

import com.ilevent.ilevent_backend.voucher.dto.VoucherRequestDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;


public interface VoucherService {
    VoucherResponseDto createVoucher(VoucherRequestDto dto);
}
