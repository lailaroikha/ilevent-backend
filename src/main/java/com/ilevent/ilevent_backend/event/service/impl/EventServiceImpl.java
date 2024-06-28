package com.ilevent.ilevent_backend.event.service.impl;

import com.ilevent.ilevent_backend.event.entity.Event;
import com.ilevent.ilevent_backend.event.repository.EventRepository;
import com.ilevent.ilevent_backend.event.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        Optional<Event> existingEventOpt = eventRepository.findById(event.getId());
        if (existingEventOpt.isPresent()) {
            Event existingEvent = existingEventOpt.get();
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setDate(event.getDate());
            return eventRepository.save(existingEvent);

        }    else {
            throw new RuntimeException("Event not found with id" + event.getId());
        }
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(()->new RuntimeException("Event not found with id"+id));
    }

    @Override
    public void deletedEvent(Long id) {
        eventRepository.deleteById(id);

    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
