package com.ilevent.ilevent_backend.voucherApply.dto;

import lombok.Data;

@Data
public class VoucherApplyRequestDto {
    private Long transactionId;
    private Long voucherId;
//    private Long promoReferralId;
    private Integer quantity;
}
