package com.ilevent.ilevent_backend.event.service;

import com.ilevent.ilevent_backend.event.entity.Event;


import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    Event updateEvent(Event event);
    Event getEventById(Long id);
    void deletedEvent(Long id);

    List<Event> getAllEvents();
}
