package com.ilevent.ilevent_backend.events.controller;



import com.ilevent.ilevent_backend.auth.helper.Claims;
import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.service.CloudinaryService;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.responses.Response;
import com.ilevent.ilevent_backend.events.controller.EventsController;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventsController {
    private static final Logger log = LoggerFactory.getLogger(EventsController.class);

    private final EventService eventService;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    public EventsController(EventService eventService, CloudinaryService cloudinaryService, ObjectMapper objectMapper) {
        this.eventService = eventService;
        this.cloudinaryService =cloudinaryService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImages(@RequestParam("file")MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is missing");
        }
        try {
            String imageUrl = cloudinaryService.uploudFile(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("image uploud failed" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<CreateEventResponseDto>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CreateEventResponseDto> events = eventService.getAllEvents(pageable);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
//    public ResponseEntity<Page<Events>> getAllEvents(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "16") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Events> events = eventService.getAllEvents(pageable);
//        return new ResponseEntity<>(events, HttpStatus.OK);
//    }

    @RolesAllowed("ROLE_ORGANIZER")
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestPart("eventImage") MultipartFile eventImage,
                                         @RequestPart("data") String createEventRequestJson) {
        try {
            // Convert JSON string to CreateEventRequestDto
            CreateEventRequestDto createEventRequestDto = objectMapper.readValue(createEventRequestJson, CreateEventRequestDto.class);

            var claims = Claims.getClaimsFromJwt();
            var email = (String) claims.get("sub");
            log.info("Create event request received for user: " + email);

            CreateEventResponseDto dto = eventService.createEvent(createEventRequestDto, email, eventImage);
            return Response.success("Event created successfully", dto);
        } catch (IOException e) {
            log.error("Error processing JSON: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body("Invalid JSON format");
        } catch (Exception e) {
            log.error("Error creating event: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the event");
        }
    }
//    @PostMapping("/create")
//    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
//
//        var claims = Claims.getClaimsFromJwt();
//        var email = (String) claims.get("sub");
//        log.info("Create event request received for user: " + email);
//        CreateEventResponseDto dto = eventService.createEvent(createEventRequestDto, email);
//        return Response.success("Event created successfully", dto);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        return Response.success("Event found", eventService.getEventById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredEvents(
            @RequestParam(required = false) Events.CategoryType category,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Boolean isFreeEvent,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CreateEventResponseDto> events = eventService.getFilteredEvents(category, date, isFreeEvent, location, keyword, pageable);
        return Response.success("Filtered events retrieved successfully", events);
    }

    @RolesAllowed("ROLE_ORGANIZER")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return Response.success("Event deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEvents(@RequestParam String keyword) {
        List<CreateEventResponseDto> events = eventService.searchEvents(keyword);
        return Response.success("Search results retrieved successfully", events);
    }


}
