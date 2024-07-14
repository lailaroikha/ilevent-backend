package com.ilevent.ilevent_backend.transaction.service.impl;

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
import org.springframework.stereotype.Service;
import com.ilevent.ilevent_backend.ticketApply.dto.TicketApplyRequestDto;

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
    public PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto priceCalculationRequestDto) {
        // Menghitung jumlah total untuk tiket
        BigDecimal totalAmount = priceCalculationRequestDto.getTickets().stream()
                .map(ticketDto -> {
                    Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
                            .orElseThrow(() -> new RuntimeException("Ticket not found"));
                    return BigDecimal.valueOf(ticket.getPriceAfterDiscount()).multiply(BigDecimal.valueOf(ticketDto.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Mengurangi jumlah dengan diskon voucher
        BigDecimal amountAfterDiscount = totalAmount;
        if (priceCalculationRequestDto.getVouchers() != null) {
            for (VoucherApplyRequestDto voucherDto : priceCalculationRequestDto.getVouchers()) {
                Voucher voucher = voucherRepository.findById(voucherDto.getVoucherId())
                        .orElseThrow(() -> new RuntimeException("Voucher not found"));
                BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(voucher.getDiscountPercentage()).divide(BigDecimal.valueOf(100)));
                amountAfterDiscount = amountAfterDiscount.subtract(discount);
            }
        }

        // Mengurangi jumlah dengan diskon promo referral
        if (priceCalculationRequestDto.getPromoReferralId() != null) {
            PromoReferral promoReferral = promoReferralRepository.findById(priceCalculationRequestDto.getPromoReferralId())
                    .orElseThrow(() -> new RuntimeException("PromoReferral not found"));
            BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(promoReferral.getPromoValueDiscount()).divide(BigDecimal.valueOf(100)));
            amountAfterDiscount = amountAfterDiscount.subtract(discount);
        }

        // Mengurangi jumlah dengan diskon poin (maksimal 50% dari harga setelah diskon)
        Users user = userRepository.findById(priceCalculationRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        BigDecimal pointsDiscount = BigDecimal.ZERO;
        if (user.getTotalPoints() > 0) {
            BigDecimal maxPointsDiscount = amountAfterDiscount.multiply(BigDecimal.valueOf(0.50));
            BigDecimal pointsToUse = BigDecimal.valueOf(user.getTotalPoints());
            pointsDiscount = pointsToUse.min(maxPointsDiscount);
            amountAfterDiscount = amountAfterDiscount.subtract(pointsDiscount);

            // Mengurangi poin pengguna
            int pointsUsed = pointsDiscount.intValue(); // 1 poin = 1 unit mata uang
            user.setTotalPoints(user.getTotalPoints() - pointsUsed);
            userRepository.save(user);
        }

        // Menyiapkan respons
        PriceCalculationResponseDto responseDto = new PriceCalculationResponseDto();
        responseDto.setTotalAmount(totalAmount.doubleValue()); // Mengonversi kembali ke Double
        responseDto.setAmountAfterDiscount(amountAfterDiscount.doubleValue()); // Mengonversi kembali ke Double
        responseDto.setPointsDiscount(pointsDiscount.doubleValue()); // Mengonversi kembali ke Double

        return responseDto;

    }

    @Override
    public TransactionResponseDto submitTransaction(TransactionRequestDto transactionRequestDto) {
        // Buat permintaan perhitungan harga
        PriceCalculationRequestDto priceCalculationRequestDto = new PriceCalculationRequestDto();
        priceCalculationRequestDto.setUserId(transactionRequestDto.getUserId());
        priceCalculationRequestDto.setTickets(transactionRequestDto.getTickets());
        priceCalculationRequestDto.setVouchers(transactionRequestDto.getVouchers());
        priceCalculationRequestDto.setPromoReferralId(transactionRequestDto.getPromoReferralId());

        // Panggil calculatePrice untuk menghitung harga
        PriceCalculationResponseDto priceCalculationResponseDto = calculatePrice(priceCalculationRequestDto);


        // Temukan pengguna dan event
        Users user = userRepository.findById(transactionRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Events event = eventRepository.findById(transactionRequestDto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Buat entitas transaksi baru
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setEvent(event);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(BigDecimal.valueOf(priceCalculationResponseDto.getAmountAfterDiscount())); // Gunakan hasil perhitungan
        transaction.setPaymentStatus("sukses");
        transaction.setCreatedAt(Instant.now());
        transaction.setUpdatedAt(Instant.now());

        // Simpan transaksi
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Update jumlah tiket yang tersedia
        for (TicketApplyRequestDto ticketDto : transactionRequestDto.getTickets()) {
            Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));
            ticket.setAvailableSeats(ticket.getAvailableSeats() - ticketDto.getQuantity());
            ticketRepository.save(ticket);

            //save to ticketapply
            TicketApply ticketApply = new TicketApply();
            ticketApply.setTransactionId(savedTransaction);
            ticketApply.setTicketId(ticket);
            ticketApply.setQuantity(ticketDto.getQuantity());
            ticketApply.setCreatedAt(Instant.now()); // Set createdAt
            ticketApply.setUpdatedAt(Instant.now()); // Set updatedAt
            ticketApplyRepository.save(ticketApply);
        }

        // Update jumlah voucher yang tersedia
        if (transactionRequestDto.getVouchers() != null) {
            for (VoucherApplyRequestDto voucherDto : transactionRequestDto.getVouchers()) {
                Voucher voucher = voucherRepository.findById(voucherDto.getVoucherId())
                        .orElseThrow(() -> new RuntimeException("Voucher not found"));
                voucher.setMaxUses(voucher.getMaxUses() - voucherDto.getQuantity());
                voucherRepository.save(voucher);

                //save to voucherapply
                VoucherApply voucherApply = new VoucherApply();
                voucherApply.setTransactionId(savedTransaction);
                voucherApply.setVoucherId(voucher);
                voucherApply.setQuantity(voucherDto.getQuantity());
                voucherApply.setCreatedAt(Instant.now()); // Set createdAt
                voucherApply.setUpdatedAt(Instant.now()); // Set updatedAt
                voucherApplyRepository.save(voucherApply);
            }
        }

        // Set detail transaksi dalam response DTO
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setTransactionId(savedTransaction.getId());
        responseDto.setTotalAmount(priceCalculationResponseDto.getTotalAmount()); // Gunakan hasil perhitungan
        responseDto.setAmountAfterDiscount(priceCalculationResponseDto.getAmountAfterDiscount()); // Gunakan hasil perhitungan
        responseDto.setPointsDiscount(priceCalculationResponseDto.getPointsDiscount()); // Gunakan hasil perhitungan
        responseDto.setPaymentStatus(transaction.getPaymentStatus());

        return responseDto;
    }
}

