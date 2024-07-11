package com.ilevent.ilevent_backend.events.dto;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateEventResponseDto {
    private Long organizerId;
    private String name;
    private String description;
    private String location;
    private String date;
    private String time;
    private String imageUrl;
    private Boolean isFreeEvent;
    private Integer eventCategoriesId;
    private Events.CategoryType category;
    private OrganizerDto organizer;

    //Ticket and Voucher
    private List<TicketResponseDto> tickets;
    private List<VoucherResponseDto> vouchers;

//    private List<TicketDto> ticket;

    @Data
    public static class OrganizerDto {
        private Long id;
        private String name;
        private String username;
    }

    public static CreateEventResponseDto fromEntity(Events events){
        CreateEventResponseDto dto = new CreateEventResponseDto();

        if (events.getOrganizer() != null) {
            dto.setOrganizerId(events.getOrganizer().getId());
            OrganizerDto organizerDto = new OrganizerDto();
            organizerDto.setId(events.getOrganizer().getId());
            organizerDto.setName(events.getOrganizer().getName());
            organizerDto.setUsername(events.getOrganizer().getUsername());
            dto.setOrganizer(organizerDto);
        }
//        dto.setOrganizerId(events.getOrganizer().getId());
        dto.setName(events.getName());
        dto.setDescription(events.getDescription());
        dto.setLocation(events.getLocation());
        dto.setDate(events.getDate().toString());
        dto.setTime(events.getTime().toString());
        dto.setImageUrl(events.getImage());
        dto.setIsFreeEvent(events.getIsFreeEvent());
        dto.setCategory(events.getCategory());

        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(events.getOrganizer().getId());
        organizerDto.setName(events.getOrganizer().getName());
        organizerDto.setUsername(events.getOrganizer().getUsername());
        dto.setOrganizer(organizerDto);

        // Tickets
        if (events.getTickets() != null) {
            dto.setTickets(events.getTickets().stream()
                    .map(TicketResponseDto::fromEntity)
                    .collect(Collectors.toList()));
        }

        // Vouchers
        if (events.getVouchers() != null) {
            dto.setVouchers(events.getVouchers().stream()
                    .map(VoucherResponseDto::fromEntity)
                    .collect(Collectors.toList()));
        }
//        if (events.getTicket() != null) {
//            dto.setTicket(events.getTicket().stream()
//                    .map(TicketDto::fromEntity)
//                    .collect(Collectors.toList()));
//        }
        return dto;
    }
}
