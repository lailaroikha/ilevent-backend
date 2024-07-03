package com.ilevent.ilevent_backend.eventcategory.dto;

import com.ilevent.ilevent_backend.eventcategory.entity.EventCategoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Data
public class EventCategoryRequestDto {
    @NotNull
    private Long eventId;

    @NotNull
    private EventCategoryType category;
}
