package com.ilevent.ilevent_backend.ticketApply.service.impl;

import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyResponseDto;
import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
import com.ilevent.ilevent_backend.ticketApply.repository.TicketApplyRepository;
import com.ilevent.ilevent_backend.ticketApply.service.TicketApplyService;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketApplyServiceImpl implements TicketApplyService {

    private final TicketApplyRepository ticketApplyRepository;
    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;

    public TicketApplyServiceImpl(TicketApplyRepository ticketApplyRepository,
                                  TransactionRepository transactionRepository,
                                  TicketRepository ticketRepository) {
        this.ticketApplyRepository = ticketApplyRepository;
        this.transactionRepository = transactionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public TicketApplyResponseDto createTicketApply(TicketApplyRequestDto ticketApplyRequestDto) {
        Transaction transaction = transactionRepository.findById(ticketApplyRequestDto.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Ticket ticket = ticketRepository.findById(ticketApplyRequestDto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketApply ticketApply = new TicketApply();
        ticketApply.setTransactionId(transaction);
        ticketApply.setTicketId(ticket);
        ticketApply.setQuantity(ticketApplyRequestDto.getQuantity());
        ticketApply.setCreatedAt(Instant.now());
        ticketApply.setUpdatedAt(Instant.now());

        TicketApply savedTicketApply = ticketApplyRepository.save(ticketApply);

        return mapToResponseDto(savedTicketApply);
    }

    @Override
    public List<TicketApplyResponseDto> getTicketAppliesByTransactionId(Transaction transactionId) {
        List<TicketApply> ticketApplies = ticketApplyRepository.findAllByTransactionId(transactionId);
        return ticketApplies.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private TicketApplyResponseDto mapToResponseDto(TicketApply ticketApply) {
        TicketApplyResponseDto responseDto = new TicketApplyResponseDto();
        responseDto.setId(ticketApply.getId());
        responseDto.setTransactionId(ticketApply.getTransactionId().getId());
        responseDto.setTicketId(ticketApply.getTicketId().getId());
        responseDto.setQuantity(ticketApply.getQuantity());
        responseDto.setCreatedAt(ticketApply.getCreatedAt());
        responseDto.setUpdatedAt(ticketApply.getUpdatedAt());
        return responseDto;
    }
}
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
//        Transaction transaction = transactionRepository.findById(ticketApplyRequestDto.getTransactionId())
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//        Ticket ticket = ticketRepository.findById(ticketApplyRequestDto.getTicketId())
//                .orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//        TicketApply ticketApply = new TicketApply();
//        ticketApply.setTransactionId(transaction);
//        ticketApply.setTicketId(ticket);
//        ticketApply.setQuantity(ticketApplyRequestDto.getQuantity());
//        ticketApply.setCreatedAt(Instant.now());
//        ticketApply.setUpdatedAt(Instant.now());
//
//        TicketApply savedTicketApply = ticketApplyRepository.save(ticketApply);
//
//        TicketApplyResponseDto responseDto = new TicketApplyResponseDto();
//        responseDto.setId(savedTicketApply.getId());
//        responseDto.setTransactionId(savedTicketApply.getTransactionId().getId());
//        responseDto.setTicketId(savedTicketApply.getTicketId().getId());
//        responseDto.setQuantity(savedTicketApply.getQuantity());
//        responseDto.setCreatedAt(savedTicketApply.getCreatedAt());
//        responseDto.setUpdatedAt(savedTicketApply.getUpdatedAt());
//
//        return responseDto;
//    }
//
//    @Override
//    public List<TicketApplyResponseDto> getTicketAppliesByTransactionId(Long transactionId) {
//        List<TicketApply> ticketApplies = ticketApplyRepository.findAllByTransactionId(transactionId);
//        return ticketApplies.stream()
//                .map(ticketApply -> {
//                    TicketApplyResponseDto responseDto = new TicketApplyResponseDto();
//                    responseDto.setId(ticketApply.getId());
//                    responseDto.setTransactionId(ticketApply.getTransactionId().getId());
//                    responseDto.setTicketId(ticketApply.getTicketId().getId());
//                    responseDto.setQuantity(ticketApply.getQuantity());
//                    responseDto.setCreatedAt(ticketApply.getCreatedAt());
//                    responseDto.setUpdatedAt(ticketApply.getUpdatedAt());
//                    return responseDto;
//                })
//                .collect(Collectors.toList());
//    }
//}
