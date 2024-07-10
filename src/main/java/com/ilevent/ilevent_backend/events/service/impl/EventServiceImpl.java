package com.ilevent.ilevent_backend.events.service.impl;

import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository){
        this.eventRepository = eventRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Page<Events> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public CreateEventResponseDto createEvent(CreateEventRequestDto dto, String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("User found: " + user.getEmail() + ", Is Organizer: " + user.getOrganizer());
        if (!Boolean.TRUE.equals(user.getOrganizer())) {
            log.error("User with email " + email + " is not an organizer");
            throw new RuntimeException("User is not an organizer");

        }
        Events events = new Events();
        events.setName(dto.getName());
        events.setDescription(dto.getDescription());
        // Parse date and time from request
        LocalDate parsedDate = LocalDate.parse(dto.getDate());
        LocalTime parsedTime = LocalTime.parse(dto.getTime());
        // Set parsed date and time to entity
        events.setDate(parsedDate);
        events.setTime(parsedTime);
        // Set organizer ke event
        events.setOrganizer(user);
        events.setLocation(dto.getLocation());
        events.setIsFreeEvent(dto.getIsFreeEvent());
        events.setImage(dto.getImage());
        events.setCategory(dto.getCategory());
        eventRepository.save(events);
        return CreateEventResponseDto.fromEntity(events);
    }


//    @Override
//    public List<CreateEventRequestDto> findByUser_Email(String email) {
//
//        return List.of();
//    }

    @Override
    public Events updateEvent(Events event) {
        Optional<Events> existingEventOpt = eventRepository.findById(event.getId());
        if (existingEventOpt.isPresent()) {
            Events existingEvent = existingEventOpt.get();
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setDate(event.getDate());
            return eventRepository.save(existingEvent);
        }    else {
            throw new RuntimeException("Event not found with id" + event.getId());
        }
    }

    @Override
    public CreateEventResponseDto getEventById(Long id) {
        Events event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
        return CreateEventResponseDto.fromEntity(event);
    }

    @Override
    public void deletedEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Events> getEventsByCategory(Events.CategoryType category) {
        return eventRepository.findByCategory(category);
    }

    @Override
    public List<Events> getEventsByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    @Override
    public List<Events> getEventsByPrice(Boolean isFreeEvent) {
        return eventRepository.findByIsFreeEvent(isFreeEvent);
    }

    @Override
    public List<Events> getEventsByAvailableSeats(Integer availableSeats) {
        return eventRepository.findByAvailableSeatsGreaterThanEqual(availableSeats);
    }

    @Override
    public List<CreateEventResponseDto> getFilteredEvents(Events.CategoryType category, LocalDate date, Boolean isFreeEvent, Integer availableSeats) {
        List<Events> events = eventRepository.findByCategoryAndDateAndIsFreeEventAndAvailableSeatsGreaterThanEqual(category, date, isFreeEvent, availableSeats);
        return events.stream().map(CreateEventResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

}
