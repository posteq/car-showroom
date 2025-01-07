package by.clevertec.controller;

import by.clevertec.dto.ReviewDto;
import by.clevertec.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> create(@RequestBody @Valid ReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.create(dto));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> findReviewById(@PathVariable("reviewId") @Valid Long reviewId) {
        ReviewDto reviewDto = reviewService.findById(reviewId);
        return ResponseEntity.ok()
                .body(reviewDto);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> findAllReviews() {
        return ResponseEntity.ok()
                .body(reviewService.findAll());
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> update(@PathVariable("reviewId") @Valid Long reviewId,
                                            @RequestBody @Valid ReviewDto reviewDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.update(reviewId, reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("reviewId") @Valid Long reviewId) {
        reviewService.delete(reviewId);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewsByRating(@PathVariable int rating) {
        return ResponseEntity.ok(reviewService.findReviewsByRating(rating));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReviewDto>> getReviewsByKeyword(@RequestParam("keyword") String keyword) {
        List<ReviewDto> reviews = reviewService.searchReviews(keyword);
        return ResponseEntity.ok(reviews);
    }
}
