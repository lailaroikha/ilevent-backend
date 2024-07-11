package com.ilevent.ilevent_backend.ticket.service;

import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;

public interface TicketService {
    TicketResponseDto createTicket(Ticket dto);
    Double calculatePriceAfterDiscount(Double priceBeforeDiscount, Double totalDiscount);
}
