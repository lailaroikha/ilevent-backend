package com.ilevent.ilevent_backend.eventcategory.dto;

import com.ilevent.ilevent_backend.eventcategory.entity.EventCategory;
import com.ilevent.ilevent_backend.eventcategory.entity.EventCategoryType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;


@Data
public class EventCategoryResponseDto {
    private Integer id;
    private Long eventId;
    private EventCategoryType category;
    private Instant createdAt;
    private Instant updatedAt;

public static EventCategoryResponseDto fromEntity(EventCategory eventCategory){
    EventCategoryResponseDto dto = new EventCategoryResponseDto();
    dto.setId(eventCategory.getId());
    dto.setEventId(eventCategory.getEvent().getId());
    dto.setCategory(eventCategory.getCategory());
    dto.setCreatedAt(eventCategory.getCreatedAt());
    dto.setUpdatedAt(eventCategory.getUpdatedAt());
    return dto;
    }
}


