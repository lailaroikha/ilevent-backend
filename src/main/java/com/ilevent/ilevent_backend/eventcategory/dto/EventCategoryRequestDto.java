package com.ilevent.ilevent_backend.eventcategory.dto;

import com.ilevent.ilevent_backend.eventcategory.entity.EventCategoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;




@Data
public class EventCategoryRequestDto {
    private Long eventId;
    private EventCategoryType category;
}
