package com.ilevent.ilevent_backend.ticketApply.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TicketApplyResponseDto {
    private Long id;
    private Long transactionId;
    private Long ticketId;
    private Integer quantity;
    private Instant createdAt;
    private Instant updatedAt;

}
