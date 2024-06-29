package com.ilevent.ilevent_backend.events.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventsRepository;
import com.ilevent.ilevent_backend.events.service.EventsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventsServiceImpl implements EventsService {
    private final EventsRepository eventsRepository;

    public EventsServiceImpl(EventsRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Events createEvent(Events event) {
        return eventsRepository.save(event);
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
