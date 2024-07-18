package com.ilevent.ilevent_backend.reviews.dto;

import com.ilevent.ilevent_backend.reviews.entity.Reviews;
import lombok.Data;

import java.time.Instant;

@Data
public class ReviewResponseDto {
    private Long id;
    private Long eventId;
    private Double rating;
    private String review;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;

    public static ReviewResponseDto fromEntity(Reviews reviews) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setId(reviews.getId());
        dto.setEventId(reviews.getEvent().getId());
        dto.setRating(reviews.getRating());
        dto.setReview(reviews.getReview());
        dto.setCreatedAt(reviews.getCreatedAt());
        dto.setUpdatedAt(reviews.getUpdateAt());
        // Menambahkan validasi untuk menangani null
        if (reviews.getUser() != null) {
            dto.setUserId(reviews.getUser().getId());
        } else {
            dto.setUserId(null);
        }
        return dto;
    }
}
