package com.ilevent.ilevent_backend.voucherApply.service.impl;

import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyRequestDto;
import com.ilevent.ilevent_backend.voucherApply.dto.VoucherApplyResponseDto;
import com.ilevent.ilevent_backend.voucherApply.entity.VoucherApply;
import com.ilevent.ilevent_backend.voucherApply.repository.VoucherApplyRepository;
import com.ilevent.ilevent_backend.voucherApply.service.VoucherApplyService;
import org.springframework.stereotype.Service;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherApplyServiceImpl implements VoucherApplyService {

    private final VoucherApplyRepository voucherApplyRepository;
    private final TransactionRepository transactionRepository;
    private final VoucherRepository voucherRepository;

    public VoucherApplyServiceImpl(VoucherApplyRepository voucherApplyRepository,
                                   TransactionRepository transactionRepository,
                                   VoucherRepository voucherRepository) {
        this.voucherApplyRepository = voucherApplyRepository;
        this.transactionRepository = transactionRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    @Transactional
    public VoucherApplyResponseDto createVoucherApply(VoucherApplyRequestDto voucherApplyRequestDto) {
        Transaction transaction = transactionRepository.findById(voucherApplyRequestDto.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Voucher voucher = voucherRepository.findById(voucherApplyRequestDto.getVoucherId())
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        VoucherApply voucherApply = new VoucherApply();
        voucherApply.setTransactionId(transaction);
        voucherApply.setVoucherId(voucher);
        voucherApply.setQuantity(voucherApplyRequestDto.getQuantity());
        voucherApply.setCreatedAt(Instant.now());
        voucherApply.setUpdatedAt(Instant.now());

        VoucherApply savedVoucherApply = voucherApplyRepository.save(voucherApply);

        return mapToResponseDto(savedVoucherApply);
    }

    @Override
    public List<VoucherApplyResponseDto> getVoucherAppliesByTransactionId(Transaction transactionId) {
        List<VoucherApply> voucherApplies = voucherApplyRepository.findAllByTransactionId(transactionId);
        return voucherApplies.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private VoucherApplyResponseDto mapToResponseDto(VoucherApply voucherApply) {
        VoucherApplyResponseDto responseDto = new VoucherApplyResponseDto();
        responseDto.setId(voucherApply.getId());
        responseDto.setTransactionId(voucherApply.getTransactionId().getId());
        responseDto.setVoucherId(voucherApply.getVoucherId().getId());
        responseDto.setQuantity(voucherApply.getQuantity());
        responseDto.setCreatedAt(voucherApply.getCreatedAt());
        responseDto.setUpdatedAt(voucherApply.getUpdatedAt());
        return responseDto;
    }
}