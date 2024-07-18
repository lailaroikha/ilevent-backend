package com.ilevent.ilevent_backend.transaction.service.impl;

import com.ilevent.ilevent_backend.auth.helper.Claims;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationRequestDto;
import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationResponseDto;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
import com.ilevent.ilevent_backend.ticketApply.repository.TicketApplyRepository;
import com.ilevent.ilevent_backend.transaction.dto.TransactionRequestDto;
import com.ilevent.ilevent_backend.transaction.dto.TransactionResponseDto;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
import com.ilevent.ilevent_backend.transaction.service.TransactionService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyRequestDto;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.voucherApply.entity.VoucherApply;
import com.ilevent.ilevent_backend.voucherApply.repository.VoucherApplyRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final VoucherRepository voucherRepository;
    private final PromoReferralRepository promoReferralRepository;
    private final EventRepository eventRepository;

    private final TicketApplyRepository ticketApplyRepository;
    private final VoucherApplyRepository voucherApplyRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, TicketRepository ticketRepository, VoucherRepository voucherRepository, PromoReferralRepository promoReferralRepository, EventRepository eventRepository, TicketApplyRepository ticketApplyRepository, VoucherApplyRepository voucherApplyRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.voucherRepository = voucherRepository;
        this.promoReferralRepository = promoReferralRepository;
        this.eventRepository = eventRepository;
        this.ticketApplyRepository = ticketApplyRepository;
        this.voucherApplyRepository = voucherApplyRepository;
    }
    @Override
    @Transactional
    public PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto priceCalculationRequestDto, String email) {
        // Find the user in the database
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.TRUE.equals(user.getOrganizer())) {
            throw new IllegalArgumentException("Organizers cannot calculate price");
        }

        BigDecimal totalAmount = priceCalculationRequestDto.getTickets().stream()
                .map(ticketDto -> {
                    Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
                            .orElseThrow(() -> new RuntimeException("Ticket not found"));
                    return BigDecimal.valueOf(ticket.getPriceAfterDiscount()).multiply(BigDecimal.valueOf(ticketDto.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal amountAfterDiscount = totalAmount;
        if (priceCalculationRequestDto.getVouchers() != null) {
            for (VoucherApplyRequestDto voucherDto : priceCalculationRequestDto.getVouchers()) {
                Voucher voucher = voucherRepository.findById(voucherDto.getVoucherId())
                        .orElseThrow(() -> new RuntimeException("Voucher not found"));
                BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(voucher.getDiscountPercentage()).divide(BigDecimal.valueOf(100)));
                amountAfterDiscount = amountAfterDiscount.subtract(discount);
            }
        }

        if (priceCalculationRequestDto.getPromoReferralId() != null) {
            PromoReferral promoReferral = promoReferralRepository.findById(priceCalculationRequestDto.getPromoReferralId())
                    .orElseThrow(() -> new RuntimeException("PromoReferral not found"));
            BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(promoReferral.getPromoValueDiscount()).divide(BigDecimal.valueOf(100)));
            amountAfterDiscount = amountAfterDiscount.subtract(discount);
        }

        BigDecimal pointsDiscount = BigDecimal.ZERO;
        if (user.getTotalPoints() > 0) {
            BigDecimal maxPointsDiscount = amountAfterDiscount.multiply(BigDecimal.valueOf(0.50));
            BigDecimal pointsToUse = BigDecimal.valueOf(user.getTotalPoints());
            pointsDiscount = pointsToUse.min(maxPointsDiscount);
            amountAfterDiscount = amountAfterDiscount.subtract(pointsDiscount);

            int pointsUsed = pointsDiscount.intValue();
            user.setTotalPoints(user.getTotalPoints() - pointsUsed);
            userRepository.save(user);
        }

        PriceCalculationResponseDto responseDto = new PriceCalculationResponseDto();
        responseDto.setTotalAmount(totalAmount.doubleValue());
        responseDto.setAmountAfterDiscount(amountAfterDiscount.doubleValue());
        responseDto.setPointsDiscount(pointsDiscount.doubleValue());

        return responseDto;
    }

    @Override
    @Transactional
    public TransactionResponseDto submitTransaction(TransactionRequestDto transactionRequestDto, String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (Boolean.TRUE.equals(user.getOrganizer())) {
            throw new IllegalArgumentException("Organizers cannot make transactions");
        }

        PriceCalculationRequestDto priceCalculationRequestDto = new PriceCalculationRequestDto();
        priceCalculationRequestDto.setUserId(user.getId());
        priceCalculationRequestDto.setTickets(transactionRequestDto.getTickets());
        priceCalculationRequestDto.setVouchers(transactionRequestDto.getVouchers());
        priceCalculationRequestDto.setPromoReferralId(transactionRequestDto.getPromoReferralId());

        PriceCalculationResponseDto priceCalculationResponseDto = calculatePrice(priceCalculationRequestDto, email);

        Events event = eventRepository.findById(transactionRequestDto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setEvent(event);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(BigDecimal.valueOf(priceCalculationResponseDto.getAmountAfterDiscount()));
        transaction.setPaymentStatus("sukses");
        transaction.setCreatedAt(Instant.now());
        transaction.setUpdatedAt(Instant.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        for (TicketApplyRequestDto ticketDto : transactionRequestDto.getTickets()) {
            Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));
            ticket.setAvailableSeats(ticket.getAvailableSeats() - ticketDto.getQuantity());
            ticketRepository.save(ticket);

            TicketApply ticketApply = new TicketApply();
            ticketApply.setTransactionId(savedTransaction);
            ticketApply.setTicketId(ticket);
            ticketApply.setQuantity(ticketDto.getQuantity());
            ticketApply.setCreatedAt(Instant.now());
            ticketApply.setUpdatedAt(Instant.now());
            ticketApplyRepository.save(ticketApply);
        }

        if (transactionRequestDto.getVouchers() != null) {
            for (VoucherApplyRequestDto voucherDto : transactionRequestDto.getVouchers()) {
                Voucher voucher = voucherRepository.findById(voucherDto.getVoucherId())
                        .orElseThrow(() -> new RuntimeException("Voucher not found"));
                voucher.setMaxUses(voucher.getMaxUses() - 1);
                voucherRepository.save(voucher);

                VoucherApply voucherApply = new VoucherApply();
                voucherApply.setTransactionId(savedTransaction);
                voucherApply.setVoucherId(voucher);
                voucherApply.setQuantity(1);
                voucherApply.setCreatedAt(Instant.now());
                voucherApply.setUpdatedAt(Instant.now());
                voucherApplyRepository.save(voucherApply);
            }
        }

        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setTransactionId(savedTransaction.getId());
        responseDto.setTotalAmount(priceCalculationResponseDto.getTotalAmount());
        responseDto.setAmountAfterDiscount(priceCalculationResponseDto.getAmountAfterDiscount());
        responseDto.setPointsDiscount(priceCalculationResponseDto.getPointsDiscount());
        responseDto.setPaymentStatus(transaction.getPaymentStatus());

        return responseDto;
    }
}
