//package com.ilevent.ilevent_backend.purchasedTickets.dto;
//
//import com.ilevent.ilevent_backend.events.entity.Events;
//import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralResponseDto;
//import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
//import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
//import lombok.Data;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//public class PurchasedTicketsResponseDto {
//    private Long id;
//    private Long organizerId;
//    private String name;
//    private String description;
//    private String location;
//    private String date;
//    private String time;
//    private String image;
//    private Boolean isFreeEvent;
//    private Integer eventCategoriesId;
//    private Events.CategoryType category;
//    private OrganizerDto organizer;
//
//    // Ticket and Voucher
//    private List<TicketResponseDto> tickets;
//    private List<VoucherResponseDto> vouchers;
//
//    private PromoReferralResponseDto promoReferral;
//
//    @Data
//    public static class OrganizerDto {
//        private Long id;
//        private String name;
//        private String username;
//    }
//
//    public static PurchasedTicketsResponseDto fromEntity(Events events){
//        PurchasedTicketsResponseDto dto = new PurchasedTicketsResponseDto();
//        dto.setId(events.getId());
//        if (events.getOrganizer() != null) {
//            dto.setOrganizerId(events.getOrganizer().getId());
//            OrganizerDto organizerDto = new OrganizerDto();
//            organizerDto.setId(events.getOrganizer().getId());
//            organizerDto.setName(events.getOrganizer().getName());
//            organizerDto.setUsername(events.getOrganizer().getUsername());
//            dto.setOrganizer(organizerDto);
//        }
//        dto.setName(events.getName());
//        dto.setDescription(events.getDescription());
//        dto.setLocation(events.getLocation());
//        dto.setDate(events.getDate().toString());
//        dto.setTime(events.getTime().toString());
//        dto.setImage(events.getImage());
//        dto.setIsFreeEvent(events.getIsFreeEvent());
//        dto.setCategory(events.getCategory());
//
//        // Tickets
//        if (events.getTickets() != null) {
//            dto.setTickets(events.getTickets().stream()
//                    .map(TicketResponseDto::fromEntity)
//                    .collect(Collectors.toList()));
//        }
//
//        // Vouchers
//        if (events.getVouchers() != null) {
//            dto.setVouchers(events.getVouchers().stream()
//                    .map(VoucherResponseDto::fromEntity)
//                    .collect(Collectors.toList()));
//        }
//
//        // Promo Referral
//        if (events.getPromoReferral() != null) {
//            dto.setPromoReferral(PromoReferralResponseDto.fromEntity(events.getPromoReferral()));
//        }
//
//        return dto;
//    }
//}