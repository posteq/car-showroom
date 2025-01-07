package by.clevertec.repository;

import by.clevertec.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

     @Query("from Review r where r.text like %:keyword%")
     List<Review> findReviewsByKeyword(@Param("keyword") String keyword);

     List<Review> findByRating(int rating);
}
