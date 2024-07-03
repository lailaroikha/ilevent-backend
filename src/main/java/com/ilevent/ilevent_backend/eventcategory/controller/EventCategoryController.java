package com.ilevent.ilevent_backend.eventcategory.controller;


import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryRequestDto;
import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilevent.ilevent_backend.eventcategory.service.EventCategoryService;




@RestController
@RequestMapping("/api/v1/event-categories")
public class EventCategoryController {
    private final EventCategoryService eventCategoryService;

        public EventCategoryController(EventCategoryService eventCategoryService) {
            this.eventCategoryService = eventCategoryService;
        }
//    @GetMapping
//    public Response<List<EventCategoryResponseDto>> getAllEventCategories() {
//        List<EventCategoryResponseDto> eventCategories = eventCategoryService.getAllEventCategories();
//        return new ResponseEntity<>(eventCategories, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<EventCategoryResponseDto> getEventCategoryById(@PathVariable Integer id) {
        EventCategoryResponseDto eventCategory = eventCategoryService.getEventCategoryById(id);
        return new ResponseEntity<>(eventCategory, HttpStatus.OK);
    }

    // Create a new event category
    @PostMapping
    public ResponseEntity<EventCategoryResponseDto> createEventCategory(@RequestBody EventCategoryRequestDto eventCategoryRequestDto) {
        EventCategoryResponseDto createdEventCategory = eventCategoryService.createEventCategory(eventCategoryRequestDto);
        return new ResponseEntity<>(createdEventCategory, HttpStatus.CREATED);
    }

    // Update an existing event category
    @PutMapping("/{id}")
    public ResponseEntity<EventCategoryResponseDto> updateEventCategory(@PathVariable Integer id, @RequestBody EventCategoryRequestDto eventCategoryRequestDto) {
        EventCategoryResponseDto updatedEventCategory = eventCategoryService.updateEventCategory(id, eventCategoryRequestDto);
        return new ResponseEntity<>(updatedEventCategory, HttpStatus.OK);
    }

    // Delete an event category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventCategory(@PathVariable Integer id) {
        eventCategoryService.deleteEventCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
