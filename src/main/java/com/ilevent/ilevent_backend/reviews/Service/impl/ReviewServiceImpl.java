package com.ilevent.ilevent_backend.reviews.Service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.reviews.Service.ReviewService;
import com.ilevent.ilevent_backend.reviews.dto.ReviewRequestDto;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;
import com.ilevent.ilevent_backend.reviews.entity.Reviews;
import com.ilevent.ilevent_backend.reviews.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, EventRepository eventRepository){
        this.reviewRepository = reviewRepository;
        this.eventRepository =eventRepository;
    }

    @Override
    public ReviewResponseDto addReview(ReviewRequestDto dto) {
        // Validate rating
        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Reviews review = new Reviews();
        review.setRating(dto.getRating());
        review.setReview(dto.getReview());
        review.setCreatedAt(Instant.now());
        review.setUpdateAt(Instant.now());

        Events event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        review.setEvent(event);

        reviewRepository.save(review);
        return ReviewResponseDto.fromEntity(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
