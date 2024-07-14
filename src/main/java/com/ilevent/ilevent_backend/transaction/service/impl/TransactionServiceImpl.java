package com.ilevent.ilevent_backend.transaction.service.impl;

import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationRequestDto;
import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationResponseDto;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
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


    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, TicketRepository ticketRepository, VoucherRepository voucherRepository, PromoReferralRepository promoReferralRepository, EventRepository eventRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.voucherRepository = voucherRepository;
        this.promoReferralRepository = promoReferralRepository;
        this.eventRepository = eventRepository;
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
        transaction.setAmount(BigDecimal.valueOf(transactionRequestDto.getAmountAfterDiscount()));
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
        }

        // Update jumlah voucher yang tersedia
        if (transactionRequestDto.getVouchers() != null) {
            for (VoucherApplyRequestDto voucherDto : transactionRequestDto.getVouchers()) {
                Voucher voucher = voucherRepository.findById(voucherDto.getVoucherId())
                        .orElseThrow(() -> new RuntimeException("Voucher not found"));
                voucher.setMaxUses(voucher.getMaxUses() - voucherDto.getQuantity());
                voucherRepository.save(voucher);
            }
        }

        // Set detail transaksi dalam response DTO
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setTransactionId(savedTransaction.getId());
        responseDto.setTotalAmount(transaction.getAmount().doubleValue());
        responseDto.setAmountAfterDiscount(transaction.getAmount().doubleValue()); // Sesuaikan ini jika ada perbedaan
        responseDto.setPointsDiscount(transactionRequestDto.getPointsDiscount());
        responseDto.setPaymentStatus(transaction.getPaymentStatus());

        return responseDto;
    }
}




//
//    @Override
//    public PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto priceCalculationRequestDto) {
//        // Menghitung jumlah total untuk tiket
//        BigDecimal totalAmount = priceCalculationRequestDto.getTickets().stream()
//                .map(ticketDto -> {
//                    Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
//                            .orElseThrow(() -> new RuntimeException("Ticket not found"));
//                    return BigDecimal.valueOf(ticket.getPriceAfterDiscount()).multiply(BigDecimal.valueOf(ticketDto.getQuantity()));
//                })
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        // Mengurangi jumlah dengan diskon voucher
//        BigDecimal amountAfterDiscount = totalAmount;
//        for (VoucherApplyRequestDto voucherDto : priceCalculationRequestDto.getVouchers()) {
//            Voucher voucher = voucherRepository.findById(voucherDto.getVoucherId())
//                    .orElseThrow(() -> new RuntimeException("Voucher not found"));
//            BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(voucher.getDiscountPercentage()).divide(BigDecimal.valueOf(100)));
//            amountAfterDiscount = amountAfterDiscount.subtract(discount);
//        }
//
//        // Mengurangi jumlah dengan diskon promo referral
//        if (priceCalculationRequestDto.getPromoReferralId() != null) {
//            PromoReferral promoReferral = promoReferralRepository.findById(priceCalculationRequestDto.getPromoReferralId())
//                    .orElseThrow(() -> new RuntimeException("PromoReferral not found"));
//            BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(promoReferral.getPromoValueDiscount()).divide(BigDecimal.valueOf(100)));
//            amountAfterDiscount = amountAfterDiscount.subtract(discount);
//        }
//
//        // Mengurangi jumlah dengan diskon poin (maksimal 20% dari total harga)
//        Users user = userRepository.findById(priceCalculationRequestDto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        BigDecimal maxPointsDiscount = amountAfterDiscount.multiply(BigDecimal.valueOf(0.20));
//        BigDecimal pointsToUse = BigDecimal.valueOf(user.getTotalPoints()).divide(BigDecimal.valueOf(2)); // Misalkan 2 poin = 1 unit mata uang
//        BigDecimal pointsDiscount = pointsToUse.min(maxPointsDiscount);
//        amountAfterDiscount = amountAfterDiscount.subtract(pointsDiscount);
//
//        // Menyiapkan respons
//        PriceCalculationResponseDto responseDto = new PriceCalculationResponseDto();
//        responseDto.setTotalAmount(totalAmount.doubleValue()); // Mengonversi kembali ke Double
//        responseDto.setAmountAfterDiscount(amountAfterDiscount.doubleValue()); // Mengonversi kembali ke Double
//        responseDto.setPointsDiscount(pointsDiscount.doubleValue()); // Mengonversi kembali ke Double
//
//        return responseDto;
//    }
//
//    @Override
//    public TransactionResponseDto submitTransaction(TransactionRequestDto transactionRequestDto) {
//        // Temukan pengguna dan event
//        Users user = userRepository.findById(transactionRequestDto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Events event = eventRepository.findById(transactionRequestDto.getEventId())
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        // Buat entitas transaksi baru
//        Transaction transaction = new Transaction();
//        transaction.setUser(user);
//        transaction.setEvent(event);
//        transaction.setTransactionDate(LocalDate.now());
//        transaction.setAmount(BigDecimal.valueOf(transactionRequestDto.getAmountAfterDiscount()));
//        transaction.setPaymentStatus("pending");
//        transaction.setCreatedAt(Instant.now());
//        transaction.setUpdatedAt(Instant.now());
//
//        // Simpan transaksi
//        Transaction savedTransaction = transactionRepository.save(transaction);
//
//        // Set detail transaksi dalam response DTO
//        TransactionResponseDto responseDto = new TransactionResponseDto();
//        responseDto.setTransactionId(savedTransaction.getId());
//        responseDto.setTotalAmount(transaction.getAmount().doubleValue());
//        responseDto.setAmountAfterDiscount(transaction.getAmount().doubleValue()); // Sesuaikan ini jika ada perbedaan
//        responseDto.setPointsDiscount(0.0); // Tambahkan jika ada diskon poin yang digunakan
//        responseDto.setPaymentStatus(transaction.getPaymentStatus());
//
//        return responseDto;
//    }
//}
