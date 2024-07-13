//package com.ilevent.ilevent_backend.voucherApply.service.impl;
//
//import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
//import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
//import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
//import com.ilevent.ilevent_backend.voucher.entity.Voucher;
//import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
//import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyRequestDto;
//import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyResponseDto;
//import com.ilevent.ilevent_backend.voucherApply.entity.VoucherApply;
//import com.ilevent.ilevent_backend.voucherApply.repository.VoucherApplyRepository;
//import com.ilevent.ilevent_backend.voucherApply.service.VoucherApplyService;
//import org.springframework.stereotype.Service;
//import com.ilevent.ilevent_backend.transaction.entity.Transaction;
//
//import java.time.Instant;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class VoucherApplyServiceImpl implements VoucherApplyService {
//
//    private final VoucherApplyRepository voucherApplyRepository;
//    private final TransactionRepository transactionRepository;
//    private final VoucherRepository voucherRepository;
//    private final PromoReferralRepository promoReferralRepository;
//
//    public VoucherApplyServiceImpl(VoucherApplyRepository voucherApplyRepository, TransactionRepository transactionRepository, VoucherRepository voucherRepository, PromoReferralRepository promoReferralRepository) {
//        this.voucherApplyRepository = voucherApplyRepository;
//        this.transactionRepository = transactionRepository;
//        this.voucherRepository = voucherRepository;
//        this.promoReferralRepository = promoReferralRepository;
//    }
//
//
//    @Override
//    public VoucherApplyResponseDto createVoucherApply(VoucherApplyRequestDto voucherApplyRequestDto) {
//        Transaction transaction = transactionRepository.findById(voucherApplyRequestDto.getTransactionId())
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//        VoucherApply voucherApply = new VoucherApply();
//        voucherApply.setTransactionId(transaction);
//
//        if (voucherApplyRequestDto.getVoucherId() != null) {
//            Voucher voucher = voucherRepository.findById(voucherApplyRequestDto.getVoucherId())
//                    .orElseThrow(() -> new RuntimeException("Voucher not found"));
//            voucherApply.setVoucherId(voucher);
//        }
//
//        if (voucherApplyRequestDto.getPromoReferralId() != null) {
//            PromoReferral promoReferral = promoReferralRepository.findById(voucherApplyRequestDto.getPromoReferralId())
//                    .orElseThrow(() -> new RuntimeException("PromoReferral not found"));
//            voucherApply.setPromoReferralId(promoReferral);
//        }
//
//        voucherApply.setQuantity(voucherApplyRequestDto.getQuantity());
//        voucherApply.setCreatedAt(Instant.now());
//        voucherApply.setUpdatedAt(Instant.now());
//
//        VoucherApply savedVoucherApply = voucherApplyRepository.save(voucherApply);
//
//        VoucherApplyResponseDto responseDto = new VoucherApplyResponseDto();
//        responseDto.setId(savedVoucherApply.getId());
//        responseDto.setTransactionId(savedVoucherApply.getTransactionId().getId());
//        responseDto.setVoucherId(savedVoucherApply.getVoucherId() != null ? savedVoucherApply.getVoucherId().getId() : null);
//        responseDto.setPromoReferralId(savedVoucherApply.getPromoReferralId() != null ? savedVoucherApply.getPromoReferralId().getId() : null);
//        responseDto.setQuantity(savedVoucherApply.getQuantity());
//        responseDto.setCreatedAt(savedVoucherApply.getCreatedAt());
//        responseDto.setUpdatedAt(savedVoucherApply.getUpdatedAt());
//
//        return responseDto;
//    }
//
//    @Override
//    public List<VoucherApplyResponseDto> getVoucherAppliesByTransactionId(Long transactionId) {
//        List<VoucherApply> voucherApplies = voucherApplyRepository.findAllByTransactionId(transactionId);
//        return voucherApplies.stream()
//                .map(voucherApply -> {
//                    VoucherApplyResponseDto responseDto = new VoucherApplyResponseDto();
//                    responseDto.setId(voucherApply.getId());
//                    responseDto.setTransactionId(voucherApply.getTransactionId().getId());
//                    responseDto.setVoucherId(voucherApply.getVoucherId() != null ? voucherApply.getVoucherId().getId() : null);
//                    responseDto.setPromoReferralId(voucherApply.getPromoReferralId() != null ? voucherApply.getPromoReferralId().getId() : null);
//                    responseDto.setQuantity(voucherApply.getQuantity());
//                    responseDto.setCreatedAt(voucherApply.getCreatedAt());
//                    responseDto.setUpdatedAt(voucherApply.getUpdatedAt());
//                    return responseDto;
//                })
//                .collect(Collectors.toList());
//    }
//}
