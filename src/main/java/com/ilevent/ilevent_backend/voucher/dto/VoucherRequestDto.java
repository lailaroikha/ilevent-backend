package com.ilevent.ilevent_backend.voucher.dto;

import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherRequestDto {

    private String discountCode;
    private Double discountPercentage;
    private Integer maxUses;
    private LocalDate expiredAt;

}
