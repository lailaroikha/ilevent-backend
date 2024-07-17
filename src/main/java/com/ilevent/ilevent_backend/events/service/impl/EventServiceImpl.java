package com.ilevent.ilevent_backend.events.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.ilevent.ilevent_backend.events.dto.CreateEventRequestDto;
import com.ilevent.ilevent_backend.events.dto.CreateEventResponseDto;
import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.events.service.EventService;
import com.ilevent.ilevent_backend.promoReferral.dto.PromoReferralRequestDto;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.promoReferral.repository.PromoReferralRepository;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import com.ilevent.ilevent_backend.ticket.dto.TicketResponseDto;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import com.ilevent.ilevent_backend.ticket.service.TicketService;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.voucher.dto.VoucherResponseDto;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import com.ilevent.ilevent_backend.voucher.repository.VoucherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloudinary.Cloudinary;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketService ticketService;
    private final VoucherRepository voucherRepository;
    private final TicketRepository ticketRepository;
    private final PromoReferralRepository promoReferralRepository;
    private final Cloudinary cloudinary;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, TicketService ticketService, VoucherRepository voucherRepository, TicketRepository ticketRepository, PromoReferralRepository promoReferralRepository, Cloudinary cloudinary){
        this.eventRepository = eventRepository;
        this.userRepository=userRepository;
        this.ticketService = ticketService;
        this.voucherRepository = voucherRepository;
        this.ticketRepository = ticketRepository;
        this.promoReferralRepository = promoReferralRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public Page<CreateEventResponseDto> getAllEvents(Pageable pageable) {
        Page<Events> eventsPage = eventRepository.findAll(pageable);
        return eventsPage.map(CreateEventResponseDto::fromEntity);
    }

    @Transactional
    @Override
    public CreateEventResponseDto createEvent(CreateEventRequestDto dto, String email, MultipartFile eventImage) {
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
        events.setIsFreeEvent(dto.getIsFreeEvent());
        // Set organizer ke event
        events.setOrganizer(user);
        events.setLocation(dto.getLocation());
//        events.setIsFreeEvent(dto.getIsFreeEvent());
        // Upload image to Cloudinary
        // Upload image to Cloudinary
        if (eventImage != null && !eventImage.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(
                        eventImage.getBytes(),
                        ObjectUtils.asMap("folder", "event_images")
                );
                String imageUrl = (String) uploadResult.get("url");
                events.setImage(imageUrl);
            } catch (IOException e) {
                log.error("Error uploading image to Cloudinary", e);
                throw new RuntimeException("Failed to upload image");
            }
        } else {
            // If no image is uploaded, set the image URL from the DTO
            events.setImage(dto.getImage());
        }
        events.setCategory(dto.getCategory());
//        eventRepository.save(events);
        Events savedEvent = eventRepository.save(events);
        // Buat tiket
        List<Ticket> savedTickets = null;
        if (dto.getTickets() != null) {
            savedTickets = dto.getTickets().stream()
                    .map(ticketDto -> {
//            for (TicketRequestDto ticketDto : dto.getTicket())
                        Ticket ticket = new Ticket();
                        ticket.setEventId(savedEvent);
                        ticket.setNameTicket(ticketDto.getNameTicket());
                        ticket.setAvailableSeats(ticketDto.getAvailableSeats());
                        ticket.setPriceBeforeDiscount(ticketDto.getPriceBeforeDiscount());
                        ticket.setTotalDiscount(ticketDto.getTicketDiscount());
                        ticket.setPriceAfterDiscount(ticketService.calculatePriceAfterDiscount(ticketDto.getPriceBeforeDiscount(), ticketDto.getTicketDiscount()));
                        ticket.setCreatedAt(Instant.now());
                        ticket.setUpdatedAt(Instant.now());
                        return ticketRepository.save(ticket);
                    })
                    .collect(Collectors.toList());
        }

        // Buat voucher
        List<Voucher> savedVouchers = null;
        if (dto.getVouchers() != null) {
            savedVouchers = dto.getVouchers().stream()
                    .map(voucherDto -> {
//            for (VoucherRequestDto voucherDto : dto.getVouchers()) {
                        Voucher voucher = new Voucher();
                        voucher.setEventId(savedEvent);
                        voucher.setDiscountCode(voucherDto.getDiscountCode());
                        voucher.setDiscountPercentage(voucherDto.getDiscountPercentage());
                        voucher.setMaxUses(voucherDto.getMaxUses());
                        voucher.setExpiredAt(voucherDto.getExpiredAt());
                        voucher.setCreatedAt(Instant.now());
                        voucher.setUpdatedAt(Instant.now());
                        return voucherRepository.save(voucher);
                    })
                    .collect(Collectors.toList());
//        }
//        return CreateEventResponseDto.fromEntity(savedEvent);
        }

        // Create promo referral
        if (dto.getPromoReferral() != null) {
            PromoReferralRequestDto promoReferralDto = dto.getPromoReferral();
            PromoReferral promoReferral = new PromoReferral();
            promoReferral.setEventsId(savedEvent);
            promoReferral.setUsersId(user);
            promoReferral.setPromoValueDiscount(10); // Always 10% discount for referral
            promoReferral.setStart(promoReferralDto.getStart());
            promoReferral.setEnd(promoReferralDto.getEnd());
            promoReferral.setMaxClaims(promoReferralDto.getMaxClaims());
            promoReferral.setUsed(0);
            promoReferral.setCreatedAt(Instant.now());
            promoReferral.setUpdateAt(Instant.now());
            savedEvent.setPromoReferral(promoReferral); // Link promo referral to event
            promoReferralRepository.save(promoReferral);
        }

        CreateEventResponseDto responseDto = CreateEventResponseDto.fromEntity(savedEvent);
        if (savedTickets != null) {
            responseDto.setTickets(savedTickets.stream()
                    .map(TicketResponseDto::fromEntity)
                    .collect(Collectors.toList()));
        }
        if (savedVouchers != null) {
            responseDto.setVouchers(savedVouchers.stream()
                    .map(VoucherResponseDto::fromEntity)
                    .collect(Collectors.toList()));
        }

        return responseDto;
    }

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
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<CreateEventResponseDto> searchEvents(String keyword) {
        Specification<Events> spec = new Specification<Events>() {
            @Override
            public Predicate toPredicate(Root<Events> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.or(
                        cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("location")), "%" + keyword.toLowerCase() + "%")
                );
            }
        };

        List<Events> events = eventRepository.findAll(spec);
        return events.stream().map(CreateEventResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<CreateEventResponseDto> getFilteredEvents(Events.CategoryType category, LocalDate date, Boolean isFreeEvent, String location, String keyword,  Pageable pageable) {
        log.info("Filtering events with parameters - category: {}, date: {}, isFreeEvent: {}, location: {}, keyword");

        Specification<Events> spec = new Specification<Events>() {
            @Override
            public Predicate toPredicate(Root<Events> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (category != null) {
                    predicates.add(cb.equal(root.get("category"), category));
                }
                if (date != null) {
                    predicates.add(cb.equal(root.get("date"), date));
                }
                if (isFreeEvent != null) {
                    predicates.add(cb.equal(root.get("isFreeEvent"), isFreeEvent));
                }
                if (location != null && !location.isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
                }
                if (keyword != null && !keyword.isEmpty()) {
                    String keywordPattern = "%" + keyword.toLowerCase() + "%";
                    Predicate namePredicate = cb.like(cb.lower(root.get("name")), keywordPattern);
                    Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), keywordPattern);
                    predicates.add(cb.or(namePredicate, descriptionPredicate));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<Events> eventsPage = eventRepository.findAll(spec, pageable);
        log.info("Found {} events");

        return eventsPage.map(CreateEventResponseDto::fromEntity);
    }
}
