package com.ilevent.ilevent_backend.eventcategory.service.impl;

import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryRequestDto;
import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryResponseDto;
import com.ilevent.ilevent_backend.eventcategory.entity.EventCategory;
import com.ilevent.ilevent_backend.eventcategory.repository.EventCategoryRepository;
import com.ilevent.ilevent_backend.eventcategory.service.EventCategoryService;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EventCategoryServiceImpl implements EventCategoryService {

    private final EventCategoryRepository eventCategoryRepository;
    private final EventRepository eventRepository;

    public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepository, EventRepository eventRepository) {
        this.eventCategoryRepository = eventCategoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<EventCategoryResponseDto> getAllEventCategories(Integer id) {
        return Optional.empty();
    }

    @Override
    public EventCategoryResponseDto createEventCategory(EventCategoryRequestDto eventCategoryRequestDto) {
        EventCategory eventCategory = new EventCategory();
        return null;
    }

    @Override
    public EventCategoryResponseDto updateEventCategory(Integer id, EventCategoryRequestDto eventCategoryRequestDto) {
        return null;
    }

    @Override
    public void deleteEventCategory(Integer id) {

    }
}
