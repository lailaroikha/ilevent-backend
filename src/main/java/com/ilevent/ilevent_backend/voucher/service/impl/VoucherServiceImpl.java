package com.ilevent.ilevent_backend.voucher.service.impl;

import com.ilevent.ilevent_backend.voucher.dto.VoucherRequestDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
import com.ilevent.ilevent_backend.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    @Override
    public VoucherResponseDto createVoucher(VoucherRequestDto dto) {
        Voucher voucher = new Voucher();
        voucher.setDiscountCode(dto.getDiscountCode());
        voucher.setDiscountPercentage(dto.getDiscountPercentage());
        voucher.setMaxUses(dto.getMaxUses());
        voucher.setCreatedAt(Instant.now());
        voucher.setUpdatedAt(Instant.now());
        voucher.setExpiredAt(dto.getExpiredAt()); // Use the provided expiration date for organizer-created vouchers
//        voucherRepository.save(voucher);
        Voucher savedVoucher = voucherRepository.save(voucher);
        return VoucherResponseDto.fromEntity(savedVoucher);
    }

}


