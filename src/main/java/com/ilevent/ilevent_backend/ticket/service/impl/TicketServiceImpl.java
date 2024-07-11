package com.ilevent.ilevent_backend.ticket.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import com.ilevent.ilevent_backend.ticket.service.TicketService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public TicketResponseDto createTicket(Ticket dto) {
        Events event = eventRepository.findById(dto.getEventId().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Ticket ticket = new Ticket();
        ticket.setEventId(event);
        ticket.setNameTicket(dto.getNameTicket());
        ticket.setAvailableSeats(dto.getAvailableSeats());
        ticket.setPriceBeforeDiscount(dto.getPriceBeforeDiscount());
        ticket.setPriceAfterDiscount(ticket.getPriceAfterDiscount());
        ticket.setTotalDiscount(dto.getTotalDiscount());
        ticket.setCreatedAt(Instant.now());
        ticket.setUpdatedAt(Instant.now());

        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketResponseDto.fromEntity(savedTicket);
    }

    public Double calculatePriceAfterDiscount(Double priceBeforeDiscount, Double totalDiscount) {
        if (priceBeforeDiscount == null || totalDiscount == null) {
            return null;
        }
        return priceBeforeDiscount * ((100 - totalDiscount) / 100.0);
    }
}
