package com.ilevent.ilevent_backend.voucherApply.dto;

import lombok.Data;

import java.time.Instant;
@Data
public class VoucherApplyResponseDto {
    private Long id;
    private Long transactionId;
    private Long voucherId;
    private Integer quantity;
    private Instant createdAt;
    private Instant updatedAt;
}
