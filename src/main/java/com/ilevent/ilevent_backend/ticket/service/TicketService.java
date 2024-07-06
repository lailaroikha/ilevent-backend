package com.ilevent.ilevent_backend.ticket.service;

import com.ilevent.ilevent_backend.ticket.dto.TicketRequestDto;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto dto);
}
