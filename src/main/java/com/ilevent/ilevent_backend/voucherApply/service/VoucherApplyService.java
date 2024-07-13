package com.ilevent.ilevent_backend.voucherApply.service;

import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyRequestDto;
import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyResponseDto;

import java.util.List;

public interface VoucherApplyService {
    VoucherApplyResponseDto createVoucherApply(VoucherApplyRequestDto voucherApplyRequestDto);
    List<VoucherApplyResponseDto> getVoucherAppliesByTransactionId(Long transactionId);
}
