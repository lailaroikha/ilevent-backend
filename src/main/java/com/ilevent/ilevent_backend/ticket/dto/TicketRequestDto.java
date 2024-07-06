package com.ilevent.ilevent_backend.ticket.dto;

import lombok.Data;

@Data
public class TicketRequestDto {
    private Long eventId;
    private String nameTicket;
    private Integer availableSeats;
    private Integer priceBeforeDiscount;
}
