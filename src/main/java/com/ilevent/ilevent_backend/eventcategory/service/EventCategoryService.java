package com.ilevent.ilevent_backend.eventcategory.service;

import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryRequestDto;
import com.ilevent.ilevent_backend.eventcategory.dto.EventCategoryResponseDto;
import com.ilevent.ilevent_backend.eventcategory.entity.EventCategory;

import java.util.List;

public interface EventCategoryService {
    List<EventCategoryResponseDto> getAllEventCategories(Integer id);
    EventCategoryResponseDto createEventCategory(EventCategoryRequestDto eventCategoryRequestDto);
    EventCategoryResponseDto updateEventCategory(Integer id, EventCategoryRequestDto eventCategoryRequestDto);
    EventCategoryResponseDto getEventCategoryById(Integer id);
    void deleteEventCategory(Integer id);
}
