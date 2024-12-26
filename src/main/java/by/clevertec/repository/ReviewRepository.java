package by.clevertec.repository;

import by.clevertec.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

     List<Review> searchByKeyword(String keyword);

     List<Review> searchByRating(Integer rating);
}
