package com.ilevent.ilevent_backend.transaction.dto;

import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;
import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class TransactionRequestDto {
    private Long eventId;
    private List<TicketApplyRequestDto> tickets;
    private List<VoucherApplyRequestDto> vouchers;
    private Long promoReferralId;
//    private Double amountAfterDiscount;
    private Double pointsDiscount;
    private boolean usePoints;
}
