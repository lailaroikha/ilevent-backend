package com.ilevent.ilevent_backend.events.controller;


import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.responses.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/events")
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;

    }
    @GetMapping
    public ResponseEntity<Page<Events>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Events> events = eventService.getAllEvents(pageable);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
        eventService.createEvent(createEventRequestDto);
        return Response.success("Event created successfully", eventService.createEvent(createEventRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        return Response.success("Event found", eventService.getEventById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredEvents(
            @RequestParam(required = false) Events.CategoryType category,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Boolean isFreeEvent,
            @RequestParam(required = false) Integer availableSeats
    ) {
        List<CreateEventResponseDto> events = eventService.getFilteredEvents(category, date, isFreeEvent, availableSeats);
        return Response.success("Filtered events retrieved successfully", events);
    }

}
