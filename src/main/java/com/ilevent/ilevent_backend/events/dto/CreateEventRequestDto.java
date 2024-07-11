package com.ilevent.ilevent_backend.events.dto;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.ticket.dto.TicketRequestDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateEventRequestDto {
    private String name;
    private String description;
    private String location;
    private String date;
    private String time;
    private String image;
    private Boolean isFreeEvent;
    private Events.CategoryType category;

    //createTicket
    private List<TicketRequestDto> tickets;
    //createVoucher
    private List<VoucherRequestDto> vouchers;

}
