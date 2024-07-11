//package com.ilevent.ilevent_backend.events.dto;
//
//import com.ilevent.ilevent_backend.ticket.entity.Ticket;
//import lombok.Data;
//@
//        Data
//public class TicketDto {
//    private Long id;
//    private String nameTicket;
//    private Integer availableSeats;
//    private Double priceBeforeDiscount;
//
//    public static TicketDto fromEntity(Ticket ticket) {
//        TicketDto dto = new TicketDto();
//        dto.setId(ticket.getId());
//        dto.setNameTicket(ticket.getNameTicket());
//        dto.setAvailableSeats(ticket.getAvailableSeats());
//        dto.setPriceBeforeDiscount(ticket.getPriceBeforeDiscount());
//        return dto;
//    }
//}
