package com.ilevent.ilevent_backend.ticketApply.service;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyResponseDto;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;

import java.util.List;


public interface TicketApplyService {
    TicketApplyResponseDto createTicketApply(TicketApplyRequestDto ticketApplyRequestDto);
    List<TicketApplyResponseDto> getTicketAppliesByTransactionId(Transaction transactionId);
}
