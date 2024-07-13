//package com.ilevent.ilevent_backend.ticketApply.service.impl;
//
//import com.ilevent.ilevent_backend.ticket.entity.Ticket;
//import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
//import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;
//import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyResponseDto;
//import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
//import com.ilevent.ilevent_backend.ticketApply.repository.TicketApplyRepository;
//import com.ilevent.ilevent_backend.ticketApply.service.TicketApplyService;
//import com.ilevent.ilevent_backend.transaction.entity.Transaction;
//import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.List;
//
//@Service
//public class TicketApplyServiceImpl implements TicketApplyService {
//
//    private final TicketApplyRepository ticketApplyRepository;
//    private final TransactionRepository transactionRepository;
//    private final TicketRepository ticketRepository;
//
//    public TicketApplyServiceImpl(TicketApplyRepository ticketApplyRepository, TransactionRepository transactionRepository, TicketRepository ticketRepository) {
//        this.ticketApplyRepository = ticketApplyRepository;
//        this.transactionRepository = transactionRepository;
//        this.ticketRepository = ticketRepository;
//    }
//
//    @Override
//    public TicketApplyResponseDto createTicketApply(TicketApplyRequestDto ticketApplyRequestDto) {
////        // Retrieve the Transaction entity using the provided transactionId
////        Transaction transaction = transactionRepository.findById(ticketApplyRequestDto.getTransactionId())
////                .orElseThrow(() -> new RuntimeException("Transaction not found"));
////
////        // Retrieve the Ticket entity using the provided ticketId
////        Ticket ticket = ticketRepository.findById(ticketApplyRequestDto.getTicketId())
////                .orElseThrow(() -> new RuntimeException("Ticket not found"));
////
////        // Create a new TicketApply entity
////        TicketApply ticketApply = new TicketApply();
////        // Set the Transaction and Ticket entities on the TicketApply entity
////        ticketApply.setTransaction(transaction);
////        ticketApply.setTicket(ticket);
////        // Set the quantity and timestamps on the TicketApply entity
////        ticketApply.setQuantity(ticketApplyRequestDto.getQuantity());
////        ticketApply.setCreatedAt(Instant.now());
////        ticketApply.setUpdatedAt(Instant.now());
////
////        // Save the new TicketApply entity to the database
////        TicketApply savedTicketApply = ticketApplyRepository.save(ticketApply);
////
////        // Convert the saved TicketApply entity to TicketApplyResponseDto and return it
////        TicketApplyResponseDto responseDto = new TicketApplyResponseDto();
////        responseDto.setId(savedTicketApply.getId());
////        responseDto.setTransactionId(savedTicketApply.getTransaction().getId());
////        responseDto.setTicketId(savedTicketApply.getTicket().getId());
////        responseDto.setQuantity(savedTicketApply.getQuantity());
////        responseDto.setCreatedAt(savedTicketApply.getCreatedAt());
////        responseDto.setUpdatedAt(savedTicketApply.getUpdatedAt());
//
//        return null;
//    }
//
//    @Override
//    public List<TicketApplyResponseDto> getTicketAppliesByTransactionId(Long transactionId) {
//        return List.of();
//    }
//}
