package com.ilevent.ilevent_backend.events.controller;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;

    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
        eventService.createEvent(createEventRequestDto);
        return ResponseEntity.ok("Event created successfully");
    }
}
