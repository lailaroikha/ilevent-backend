package com.ilevent.ilevent_backend.events.service.impl;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.events.service.EventService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventsRepository;

    public EventServiceImpl(EventRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Events createEvent(CreateEventRequestDto dto) {
        Events events = new Events();
        events.setName(dto.getName());
        events.setDescription(dto.getDescription());
        events.setDate(Instant.parse(dto.getDate()));
        return eventsRepository.save(events);
    }

    @Override
    public Events updateEvent(Events event) {
        Optional<Events> existingEventOpt = eventsRepository.findById(event.getId());
        if (existingEventOpt.isPresent()) {
            Events existingEvent = existingEventOpt.get();
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setDate(event.getDate());
            return eventsRepository.save(existingEvent);

        }    else {
            throw new RuntimeException("Event not found with id" + event.getId());
        }
    }

    @Override
    public Events getEventById(Long id) {
        return eventsRepository.findById(id).orElseThrow(()->new RuntimeException("Event not found with id"+id));
    }

    @Override
    public void deletedEvent(Long id) {
        eventsRepository.deleteById(id);

    }

    @Override
    public List<Events> getAllEvents() {
        return eventsRepository.findAll();
    }

}
