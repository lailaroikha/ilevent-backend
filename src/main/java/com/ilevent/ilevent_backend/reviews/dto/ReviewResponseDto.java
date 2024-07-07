package com.ilevent.ilevent_backend.reviews.dto;

import com.ilevent.ilevent_backend.reviews.entity.Review;
import lombok.Data;

import java.time.Instant;

@Data
public class ReviewResponseDto {
    private Long id;
    private Long eventId;
    private Integer rating;
    private String review;
    private Instant createdAt;
    private Instant updatedAt;

    public static ReviewResponseDto fromEntity(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setId(review.getId());
        dto.setEventId(review.getEvent().getId());
        dto.setRating(review.getRating());
        dto.setReview(review.getReview());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdateAt());
        return dto;
    }
}
