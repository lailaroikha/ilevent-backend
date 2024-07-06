package com.ilevent.ilevent_backend.ticket.dto;

import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import lombok.Data;

@Data
public class TicketResponseDto {
    private Long Id;
    private Long eventId;
    private String nameTicket;
    private Integer availableSeats;
    private Integer priceBeforeDiscount;
    private String createdAt;
    private String updatedAt;

    public static TicketResponseDto fromEntity(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setEventId(ticket.getEventId().getId());
        dto.setNameTicket(ticket.getNameTicket());
        dto.setAvailableSeats(ticket.getAvailableSeats());
        dto.setPriceBeforeDiscount(ticket.getPriceBeforeDiscount());
        dto.setCreatedAt(ticket.getCreatedAt().toString());
        dto.setUpdatedAt(ticket.getUpdatedAt().toString());
        return dto;
    }
}
