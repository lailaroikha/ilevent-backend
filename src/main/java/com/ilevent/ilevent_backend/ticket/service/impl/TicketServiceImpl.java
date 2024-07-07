package com.ilevent.ilevent_backend.ticket.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.ticket.dto.TicketRequestDto;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import com.ilevent.ilevent_backend.ticket.service.TicketService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TickerServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public TickerServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public TicketResponseDto createTicket(TicketRequestDto dto) {
        Events event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Ticket ticket = new Ticket();
        ticket.setEventId(event);
        ticket.setNameTicket(dto.getNameTicket());
        ticket.setAvailableSeats(dto.getAvailableSeats());
        ticket.setPriceBeforeDiscount(dto.getPriceBeforeDiscount());
        ticket.setCreatedAt(Instant.now());
        ticket.setUpdatedAt(Instant.now());

        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketResponseDto.fromEntity(savedTicket);
    }
}
