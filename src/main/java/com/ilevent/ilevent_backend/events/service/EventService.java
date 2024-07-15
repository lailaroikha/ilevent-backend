package com.ilevent.ilevent_backend.events.service;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    CreateEventResponseDto createEvent(CreateEventRequestDto dto, String email);
//    List<CreateEventRequestDto> findByUser_Email(String email);

    Events updateEvent(Events event);
    CreateEventResponseDto getEventById(Long id);
    void deletedEvent(Long id);
    Page<Events> getAllEvents(Pageable pageable);

    List<Events> getEventsByCategory(Events.CategoryType category);

    List<Events> getEventsByDate(LocalDate date);

    List<Events> getEventsByPrice(Boolean isFreeEvent);

    // Combine filters
    List<CreateEventResponseDto> getFilteredEvents(Events.CategoryType category, LocalDate date, Boolean isFreeEvent);

    void deleteEvent(Long id);
}
