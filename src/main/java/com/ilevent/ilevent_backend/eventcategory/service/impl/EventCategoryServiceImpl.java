package com.ilevent.ilevent_backend.eventcategory.service.impl;

import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryRequestDto;
import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryResponseDto;
import com.ilevent.ilevent_backend.eventcategory.entity.EventCategory;
import com.ilevent.ilevent_backend.eventcategory.repository.EventCategoryRepository;
import com.ilevent.ilevent_backend.eventcategory.service.EventCategoryService;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.eventcategory.entity.EventCategoryType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventCategoryServiceImpl implements EventCategoryService {

    private final EventCategoryRepository eventCategoryRepository;
    private final EventRepository eventRepository;

    public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepository, EventRepository eventRepository) {
        this.eventCategoryRepository = eventCategoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventCategoryResponseDto> getAllEventCategories(Integer id) {
        return eventCategoryRepository.findAll().stream()
                .map(EventCategoryResponseDto::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EventCategoryResponseDto createEventCategory(EventCategoryRequestDto dto) {
        EventCategory eventCategory = new EventCategory();
        Events event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id " + dto.getEventId()));
        eventCategory.setCategory(dto.getCategory());
        eventCategory.setCreatedAt(Instant.now());
        eventCategory.setUpdatedAt(Instant.now());
        eventCategoryRepository.save(eventCategory);

        return EventCategoryResponseDto.fromEntity(eventCategory);
    }

    @Override
    public EventCategoryResponseDto getEventCategoryById(Integer id) {
        EventCategory eventCategory = eventCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EventCategory not found with id " + id));
        return EventCategoryResponseDto.fromEntity(eventCategory);
    }

    @Override
    public EventCategoryResponseDto updateEventCategory(Integer id, EventCategoryRequestDto dto) {
        Optional<EventCategory> existingEventCategoryOpt = eventCategoryRepository.findById(id);
        if (existingEventCategoryOpt.isPresent()) {
            EventCategory existingEventCategory = existingEventCategoryOpt.get();
            Events event = eventRepository.findById(dto.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + dto.getEventId()));
            existingEventCategory.setCategory(dto.getCategory());
            existingEventCategory.setUpdatedAt(Instant.now());
            eventCategoryRepository.save(existingEventCategory);

            return EventCategoryResponseDto.fromEntity(existingEventCategory);
        } else {
            throw new RuntimeException("EventCategory not found with id " + id);
        }
    }


    @Override
    public void deleteEventCategory(Integer id) {
        if (!eventCategoryRepository.existsById(id)) {
            throw new RuntimeException("EventCategory not found with id " + id);
        }
        eventCategoryRepository.deleteById(id);
    }

}
