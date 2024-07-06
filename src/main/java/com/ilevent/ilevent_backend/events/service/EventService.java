package com.ilevent.ilevent_backend.events.service;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    CreateEventResponseDto createEvent(CreateEventRequestDto dto);
    Events updateEvent(Events event);
    CreateEventResponseDto getEventById(Long id);
    void deletedEvent(Long id);
    Page<Events> getAllEvents(Pageable pageable);
}
