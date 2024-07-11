//package com.ilevent.ilevent_backend.ticket.controller;
//
//import com.ilevent.ilevent_backend.responses.Response;
//import com.ilevent.ilevent_backend.ticket.dto.TicketRequestDto;
//import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
//import com.ilevent.ilevent_backend.ticket.service.TicketService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/tickets")
//public class TicketController {
//    private final TicketService ticketService;
//
//    public TicketController(TicketService ticketService) {
//        this.ticketService = ticketService;
//    }
//    @PostMapping("/create")
//    public ResponseEntity<?> createTicket(@RequestBody TicketRequestDto TicketRequestDto) {
//        TicketResponseDto ticket = ticketService.createTicket(TicketRequestDto);
//        return Response.success("Ticket created successfully", ticket);
//    }
//
//}
