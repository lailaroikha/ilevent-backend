package com.ilevent.ilevent_backend.reviews.dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private Long eventId;
    private Double rating;
    private String review;
}
