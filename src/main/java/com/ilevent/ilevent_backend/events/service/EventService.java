package com.ilevent.ilevent_backend.events.service;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.entity.Events;


import java.util.List;

public interface EventService {
    Events createEvent(CreateEventRequestDto dto);
    Events updateEvent(Events event);
    Events getEventById(Long id);
    void deletedEvent(Long id);
    List<Events> getAllEvents();
}
