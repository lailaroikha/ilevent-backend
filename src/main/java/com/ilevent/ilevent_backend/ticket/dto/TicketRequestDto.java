package com.ilevent.ilevent_backend.ticket.dto;

import lombok.Data;

@Data
public class TicketRequestDto {
    private String nameTicket;
    private Integer availableSeats;
    private Double priceBeforeDiscount;
    private Double ticketDiscount;
}
