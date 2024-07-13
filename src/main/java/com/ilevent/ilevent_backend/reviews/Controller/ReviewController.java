package com.ilevent.ilevent_backend.reviews.Controller;


import com.ilevent.ilevent_backend.responses.Response;
import com.ilevent.ilevent_backend.reviews.Service.ReviewService;
import com.ilevent.ilevent_backend.reviews.dto.ReviewRequestDto;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.addReview(reviewRequestDto);
        return Response.success("Review added successfully", reviewResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return Response.success("Review deleted successfully", null);
    }
}
