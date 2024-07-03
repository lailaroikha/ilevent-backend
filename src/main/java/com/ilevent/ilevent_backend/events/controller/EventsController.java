package com.ilevent.ilevent_backend.events.controller;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
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
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
        eventService.createEvent(createEventRequestDto);
        return Response.success("Event created successfully", eventService.createEvent(createEventRequestDto));
    }
}
