package by.clevertec.mapper;

import by.clevertec.dto.ReviewDto;
import by.clevertec.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarMapper.class, ClientMapper.class})
public interface ReviewMapper {

    ReviewDto toReviewDto(Review review);

    Review toReview(ReviewDto reviewDto);

    List<ReviewDto> toReviewDtoList(List<Review> reviews);
}
