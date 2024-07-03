package com.ilevent.ilevent_backend.eventcategory.dto;

import com.ilevent.ilevent_backend.eventcategory.entity.EventCategoryType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Data
public class EventCategoryResponseDto {
    private Integer id;
    private Long eventId;
    private EventCategoryType category;
    private Instant createdAt;
    private Instant updatedAt;
}
