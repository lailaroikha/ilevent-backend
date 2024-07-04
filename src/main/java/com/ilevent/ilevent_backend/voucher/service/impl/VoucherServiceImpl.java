package com.ilevent.ilevent_backend.voucher.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.voucher.dto.VoucherRequestDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
import com.ilevent.ilevent_backend.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.voucherRepository = voucherRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public VoucherResponseDto createVoucher(VoucherRequestDto voucherRequestDTO) {
        Optional<Users> userOptional = userRepository.findById(voucherRequestDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<Events> eventOptional = eventRepository.findById(voucherRequestDTO.getEventId());
        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }

        Users user = userOptional.get();
        Events event = eventOptional.get();
        Voucher voucher = new Voucher();
        voucher.setUserId(user);
        voucher.setEvent(event);
        voucher.setDiscountCode(voucherRequestDTO.getDiscountCode());
        voucher.setDiscountPercentage(voucherRequestDTO.getDiscountPercentage());
        voucher.setMaxUses(voucherRequestDTO.getMaxUses());
        voucher.setExpiredAt(voucherRequestDTO.getExpiredAt());
        voucher.setUsed(0);  // Initialize the used field to 0
        voucherRepository.save(voucher);

        return VoucherResponseDto.fromEntity(voucher);
    }

    @Override
    public List<VoucherResponseDto> getVouchersByUserId(Long userId) {
        List<Voucher> vouchers = voucherRepository.findByUserId(userId);
        return vouchers.stream()
                .map(VoucherResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
