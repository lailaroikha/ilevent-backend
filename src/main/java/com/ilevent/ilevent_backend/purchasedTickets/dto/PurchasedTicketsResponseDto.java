package com.ilevent.ilevent_backend.purchasedTickets.dto;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralResponseDto;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PurchasedTicketsResponseDto {
    private Long id;
    private Long organizerId;
    private String name;
    private String description;
    private String location;
    private String date;
    private String time;
    private String image;
    private Boolean isFreeEvent;
    private Integer eventCategoriesId;
    private Events.CategoryType category;
    private OrganizerDto organizer;
    private List<ReviewResponseDto> reviews;


    @Data
    public static class OrganizerDto {
        private Long id;
        private String name;
        private String username;
    }
}