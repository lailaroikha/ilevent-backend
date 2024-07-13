package com.ilevent.ilevent_backend.ticketApply.service;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyResponseDto;

import java.util.List;


public interface TicketApplyService {
    TicketApplyResponseDto createTicketApply(TicketApplyRequestDto ticketApplyRequestDto);
    List<TicketApplyResponseDto> getTicketAppliesByTransactionId(Long transactionId);
}
