package com.ilevent.ilevent_backend.events.controller;


import com.ilevent.ilevent_backend.auth.helper.Claims;
import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.responses.Response;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@RestController
@RequestMapping("/api/events")
@Validated
@Log
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

    @RolesAllowed("ROLE_ORGANIZER")
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");
        log.info("Create event request received for user: " + email);
        CreateEventResponseDto dto = eventService.createEvent(createEventRequestDto, email);
        return Response.success("Event created successfully", dto);
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

    @RolesAllowed("ROLE_ORGANIZER")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return Response.success("Event deleted successfully");
    }


}
