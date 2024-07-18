package com.ilevent.ilevent_backend.reviews.Controller;


import com.ilevent.ilevent_backend.responses.Response;
import com.ilevent.ilevent_backend.reviews.Service.ReviewService;
import com.ilevent.ilevent_backend.reviews.dto.ReviewRequestDto;
import com.ilevent.ilevent_backend.reviews.dto.ReviewResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String email = jwt.getClaim("sub"); // Assuming "sub" claim contains the user's email

        ReviewResponseDto reviewResponseDto = reviewService.addReview(reviewRequestDto, email);
        return Response.success("Review added successfully", reviewResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return Response.success("Review deleted successfully", null);
    }
}
