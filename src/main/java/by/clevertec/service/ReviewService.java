package by.clevertec.service;

import by.clevertec.dto.ReviewDto;
import by.clevertec.entity.Review;
import by.clevertec.exception.ReviewNotFoundException;
import by.clevertec.mapper.ReviewMapper;
import by.clevertec.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewDto update(Long id,ReviewDto reviewDto) {
        return reviewMapper.toReviewDto(
                reviewRepository.findById(id)
                        .map(review -> {
                            Review updatedReviewDTO = reviewMapper.toReview(reviewDto);
                            review.setText(updatedReviewDTO.getText());
                            review.setRating(updatedReviewDTO.getRating());
                            review.setCar(updatedReviewDTO.getCar());
                            review.setClient(updatedReviewDTO.getClient());
                            return reviewRepository.save(review);
                        })
                        .orElseThrow(() -> new ReviewNotFoundException(id))
        );
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public ReviewDto findById(Long id) {
        return reviewMapper.toReviewDto(reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id))
        );
    }

    @Transactional
    public List<ReviewDto> findAll() {
        return reviewMapper.toReviewDtoList(reviewRepository.findAll());
    }

    @Transactional
    public ReviewDto create(ReviewDto reviewDto) {
        Review review = reviewRepository.save(reviewMapper.toReview(reviewDto));
        return reviewMapper.toReviewDto(review);
    }

    @Transactional
    public List<ReviewDto> searchReviews(String keyword) {
        return reviewMapper.toReviewDtoList(reviewRepository.findReviewsByKeyword(keyword));
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findReviewsByRating(int rating) {
        return reviewMapper.toReviewDtoList(reviewRepository.findByRating(rating));
    }
}
