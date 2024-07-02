package com.ilevent.ilevent_backend.events.controller;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable CreateEventRequestDto dto) {
        return Response.success("Event fetched successfully", eventService.getEventById(id));
    }
}
