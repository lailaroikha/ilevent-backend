package com.ilevent.ilevent_backend.events.service.impl;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import java.time.LocalTime;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventsRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventsRepository, UserRepository userRepository){
        this.eventsRepository = eventsRepository;
        this.userRepository=userRepository;
    }

    @Override
    public CreateEventResponseDto createEvent(CreateEventRequestDto dto) {
        Events events = new Events();
        events.setName(dto.getName());
        events.setDescription(dto.getDescription());
        // Parse date and time from request
        LocalDate parsedDate = LocalDate.parse(dto.getDate());
        LocalTime parsedTime = LocalTime.parse(dto.getTime());
        // Set parsed date and time to entity
        events.setDate(parsedDate);
        events.setTime(parsedTime);
        //set organizer from users
        Users organizer = userRepository.findByIdAndIsOrganizerTrue(dto.getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found or not an organizer"));
        // Set organizer ke event
        events.setOrganizer(organizer);
        events.setLocation(dto.getLocation());
        events.setIsFreeEvent(dto.getIsFreeEvent());
        events.setImage(dto.getImage());
        events.setCategory(dto.getCategory());
        eventsRepository.save(events);
        return CreateEventResponseDto.fromEntity(events);
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
