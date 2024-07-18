package com.ilevent.ilevent_backend.listEvents.dto;

import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralResponseDto;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import lombok.Data;

import java.util.List;

public class ListEventDto {
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
    private CreateEventResponseDto.OrganizerDto organizer;

    //Ticket and Voucher
    private List<TicketResponseDto> tickets;
    private List<VoucherResponseDto> vouchers;

    private PromoReferralResponseDto promoReferral;
    private List<ReviewResponseDto> reviews;

    @Data
    public static class OrganizerDto {
        private Long id;
        private String name;
        private String username;
    }
}
