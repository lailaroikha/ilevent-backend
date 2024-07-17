package com.ilevent.ilevent_backend.events.service;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    CreateEventResponseDto createEvent(CreateEventRequestDto dto, String email, MultipartFile eventImage);
    Events updateEvent(Events event);
    CreateEventResponseDto getEventById(Long id);
    void deletedEvent(Long id);
    Page<CreateEventResponseDto> getAllEvents(Pageable pageable);
    List<Events> getEventsByCategory(Events.CategoryType category);
    List<Events> getEventsByDate(LocalDate date);
    List<Events> getEventsByPrice(Boolean isFreeEvent);
    // Combine filters
    Page<CreateEventResponseDto> getFilteredEvents(Events.CategoryType category, LocalDate date, Boolean isFreeEvent, String location, String keyword, Pageable pageable);
    void deleteEvent(Long id);
    // Tambahkan metode searchEvents
    List<CreateEventResponseDto> searchEvents(String keyword);

    // New methods for filtering upcoming and completed events
    List<CreateEventResponseDto> getUpcomingEvents(Long UserId);
    List<CreateEventResponseDto> getCompletedEvents(Long userId);
//    List<CreateEventResponseDto> getUpcomingEvents();
//    List<CreateEventResponseDto> getCompletedEvents();
//    Page<CreateEventResponseDto> getUpcomingEvents(Pageable pageable);
//    Page<CreateEventResponseDto> getCompletedEvents(Pageable pageable);
}
