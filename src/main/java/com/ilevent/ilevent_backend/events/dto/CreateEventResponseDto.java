package com.ilevent.ilevent_backend.events.dto;

import com.ilevent.ilevent_backend.events.entity.Events;
import lombok.Data;

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

    @Data
    public static class OrganizerDto {
        private Long id;
        private String name;
    }

    public static CreateEventResponseDto fromEntity(Events events){
        CreateEventResponseDto dto = new CreateEventResponseDto();
        dto.setOrganizerId(events.getOrganizer().getId());
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
        dto.setOrganizer(organizerDto);

        return dto;
    }
}
