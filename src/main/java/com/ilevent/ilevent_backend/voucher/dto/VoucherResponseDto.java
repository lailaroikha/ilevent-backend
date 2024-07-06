//package com.ilevent.ilevent_backend.voucher.dto;
//
//import com.ilevent.ilevent_backend.voucher.entity.Voucher;
//import lombok.Data;
//
//import java.time.Instant;
//import java.time.LocalDate;
//
//@Data
//public class VoucherResponseDto {
//    private Integer id;
//    private Long userId;
//    private Long eventId;
//    private String discountCode;
//    private Integer discountPercentage;
//    private Integer maxUses;
//    private Integer used;
//    private LocalDate expiredAt;
//    private Instant createdAt;
//    private Instant updatedAt;
//
//    public static VoucherResponseDto fromEntity(Voucher voucher) {
//        VoucherResponseDto dto = new VoucherResponseDto();
//        dto.setId(voucher.getId());
//        dto.setUserId(voucher.getUserId().getId());
//        dto.setEventId(voucher.getEventId().getId());
//        dto.setDiscountCode(voucher.getDiscountCode());
//        dto.setDiscountPercentage(voucher.getDiscountPercentage());
//        dto.setMaxUses(voucher.getMaxUses());
//        dto.setUsed(voucher.getUsed());
//        dto.setExpiredAt(voucher.getExpiredAt());
//        dto.setCreatedAt(voucher.getCreatedAt());
//        dto.setUpdatedAt(voucher.getUpdatedAt());
//        return dto;
//    }
//}
