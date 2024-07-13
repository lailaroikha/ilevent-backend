package com.ilevent.ilevent_backend.transaction.service.impl;

import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import com.ilevent.ilevent_backend.ticketApply.repository.TicketApplyRepository;
import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
import com.ilevent.ilevent_backend.transaction.service.TransactionService;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
import com.ilevent.ilevent_backend.voucherApply.repository.VoucherApplyRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TicketApplyRepository ticketApplyRepository;
    private final VoucherApplyRepository voucherApplyRepository;
    private final TicketRepository ticketRepository;
    private final VoucherRepository voucherRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, TicketApplyRepository ticketApplyRepository, VoucherApplyRepository voucherApplyRepository, TicketRepository ticketRepository, VoucherRepository voucherRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.ticketApplyRepository = ticketApplyRepository;
        this.voucherApplyRepository = voucherApplyRepository;
        this.ticketRepository = ticketRepository;
        this.voucherRepository = voucherRepository;
    }


}
