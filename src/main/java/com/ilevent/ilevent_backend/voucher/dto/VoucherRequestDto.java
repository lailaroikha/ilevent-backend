package com.ilevent.ilevent_backend.voucher.dto;

import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherRequestDto {
    private Long userId;
    private Long eventId;
    private String discountCode;
    private Integer discountPercentage;
    private Integer maxUses;
    private LocalDate expiredAt;

}
