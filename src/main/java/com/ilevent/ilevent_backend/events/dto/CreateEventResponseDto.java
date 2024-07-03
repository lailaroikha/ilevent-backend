package com.ilevent.ilevent_backend.events.dto;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.users.entity.Users;
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
    private String organizer;
    private Boolean isFreeEvent;

    public static CreateEventResponseDto fromEntity(Events events){
        CreateEventResponseDto dto = new CreateEventResponseDto();
        dto.setOrganizerId(events.getOrganizer().getId());
        dto.setName(events.getName());
        dto.setDescription(events.getDescription());
        dto.setLocation(events.getLocation());
        dto.setDate(events.getDate().toString());
        dto.setTime(events.getTime().toString());
        dto.setImageUrl(events.getImage());
        dto.setOrganizer(events.getOrganizer().getName());
        dto.setIsFreeEvent(events.getIsFreeEvent());
        return dto;
    }
}
