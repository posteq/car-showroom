package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.entity.Review;
import by.clevertec.repository.ReviewRepository;

import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository = new ReviewRepository();

    public void updateReview(Review review) {
        reviewRepository.update(review);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review.getId());
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    public void addReview(Client client, Car car, String text, int rating) {
        reviewRepository.addReview(client, car, text, rating);
    }

    public List<Review> searchReviews(String keyword) {
        return reviewRepository.searchByKeyword(keyword);
    }

    public List<Review> searchReviewsByRating(int rating) {
        return reviewRepository.searchByRating(rating);
    }
}
