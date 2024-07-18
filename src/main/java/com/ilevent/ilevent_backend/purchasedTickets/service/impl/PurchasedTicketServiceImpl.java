package com.ilevent.ilevent_backend.purchasedTickets.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.purchasedTickets.dto.PurchasedTicketsResponseDto;
import com.ilevent.ilevent_backend.purchasedTickets.repository.PurchasedTicketRepository;
import com.ilevent.ilevent_backend.purchasedTickets.service.PurchasedTicketService;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;
import com.ilevent.ilevent_backend.reviews.repository.ReviewRepository;
import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchasedTicketServiceImpl implements PurchasedTicketService {
    private final PurchasedTicketRepository purchasedTicketsRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public PurchasedTicketServiceImpl(PurchasedTicketRepository purchasedTicketsRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.purchasedTicketsRepository = purchasedTicketsRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<PurchasedTicketsResponseDto> getPurchasedTicketsByUserId(String email, String status) {
        // Find the user in the database
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.TRUE.equals(user.getOrganizer())) {
            throw new IllegalArgumentException("Organizers cannot calculate price");
        }

        List<TicketApply> ticketApplies;

        switch (status != null ? status.toLowerCase() : "") {
            case "upcoming":
                ticketApplies = purchasedTicketsRepository.findUpcomingTicketsByUserId(user.getId());
                break;
            case "completed":
                ticketApplies = purchasedTicketsRepository.findCompletedTicketsByUserId(user.getId());
                break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }

        return ticketApplies.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PurchasedTicketsResponseDto mapToDto(TicketApply ticketApply) {
        PurchasedTicketsResponseDto dto = new PurchasedTicketsResponseDto();
        Events event = ticketApply.getTransactionId().getEvent();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate().toString());
        dto.setTime(event.getTime().toString());
        dto.setImage(event.getImage());
        dto.setIsFreeEvent(event.getIsFreeEvent());
        dto.setEventCategoriesId(event.getCategory().ordinal());
        dto.setCategory(event.getCategory());

        PurchasedTicketsResponseDto.OrganizerDto organizerDto = new PurchasedTicketsResponseDto.OrganizerDto();
        organizerDto.setId(event.getOrganizer().getId());
        organizerDto.setName(event.getOrganizer().getName());
        organizerDto.setUsername(event.getOrganizer().getUsername());
        dto.setOrganizer(organizerDto);

        List<ReviewResponseDto> reviews = reviewRepository.findByEvent(event).stream()
                .map(ReviewResponseDto::fromEntity)
                .collect(Collectors.toList());
        dto.setReviews(reviews);

        return dto;
    }
}