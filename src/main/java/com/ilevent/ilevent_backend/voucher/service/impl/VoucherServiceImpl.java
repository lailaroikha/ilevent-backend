package com.ilevent.ilevent_backend.voucher.service.impl;

import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository, EventRepository eventRepository, UserRepository userRepository){
        this.voucherRepository = voucherRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }


    @Override
    public VoucherResponseDto createVoucher(VoucherRequestDto dto) {
        Voucher voucher = new Voucher();
        voucher.setDiscountCode(dto.getDiscountCode());
        voucher.setDiscountPercentage(dto.getDiscountPercentage());
        voucher.setMaxUses(dto.getMaxUses());

        // Set the expiration date based on whether it is a referral code voucher or an organizer-created voucher
//        if (dto.getDiscountCode().startsWith("REF")) {
//            voucher.setReferralCodeVoucher(); // This method sets expiration to 3 months from creation
//        } else {
        voucher.setCreatedAt(Instant.now());
        voucher.setUpdatedAt(Instant.now());
        voucher.setExpiredAt(dto.getExpiredAt()); // Use the provided expiration date for organizer-created vouchers


//        voucherRepository.save(voucher);
        Voucher savedVoucher = voucherRepository.save(voucher);
        return VoucherResponseDto.fromEntity(savedVoucher);
    }

}


