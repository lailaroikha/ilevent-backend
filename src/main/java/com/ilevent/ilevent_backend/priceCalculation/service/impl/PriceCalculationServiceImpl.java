//package com.ilevent.ilevent_backend.priceCalculation.service.impl;
//
//import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationRequestDto;
//import com.ilevent.ilevent_backend.priceCalculation.dto.PriceCalculationResponseDto;
//import com.ilevent.ilevent_backend.priceCalculation.service.PriceCalculationService;
//import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
//import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
//import com.ilevent.ilevent_backend.users.repository.UserRepository;
//import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
//import com.ilevent.ilevent_backend.users.entity.Users;
//import com.ilevent.ilevent_backend.ticket.entity.Ticket;
//import com.ilevent.ilevent_backend.voucher.entity.Voucher;
//import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
//import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyRequestDto;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//public class PriceCalculationServiceImpl implements PriceCalculationService {
//    private final TicketRepository ticketRepository;
//    private final VoucherRepository voucherRepository;
//    private final PromoReferralRepository promoReferralRepository;
//    private final UserRepository userRepository;
//
//    public PriceCalculationServiceImpl(TicketRepository ticketRepository, VoucherRepository voucherRepository, PromoReferralRepository promoReferralRepository, UserRepository userRepository) {
//        this.ticketRepository = ticketRepository;
//        this.voucherRepository = voucherRepository;
//        this.promoReferralRepository = promoReferralRepository;
//        this.userRepository = userRepository;
//    }
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
//        BigDecimal maxPointsDiscount = totalAmount.multiply(BigDecimal.valueOf(0.20));
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
//}
