package com.ilevent.ilevent_backend.events.service;

import com.ilevent.ilevent_backend.events.entity.Events;


import java.util.List;

public interface EventsService {
    Events createEvent(Events event);
    Events updateEvent(Events event);
    Events getEventById(Long id);
    void deletedEvent(Long id);

    List<Events> getAllEvents();
}
