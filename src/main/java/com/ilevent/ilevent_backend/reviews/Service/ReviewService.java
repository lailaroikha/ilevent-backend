package com.ilevent.ilevent_backend.reviews.Service;

import com.ilevent.ilevent_backend.reviews.dto.ReviewRequestDto;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto addReview(ReviewRequestDto dto, String email);
    void deleteReview(Long id);
}
