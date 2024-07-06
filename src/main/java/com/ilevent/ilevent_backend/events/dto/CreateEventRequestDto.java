package com.ilevent.ilevent_backend.events.dto;

import com.ilevent.ilevent_backend.events.entity.Events;
import lombok.Data;
@Data
public class CreateEventRequestDto {
    private Long organizerId;
    private String name;
    private String description;
    private String location;
    private String date;
    private String time;
    private String image;
    private String organizer;
    private Boolean isFreeEvent;
    private Events.CategoryType category;

}
