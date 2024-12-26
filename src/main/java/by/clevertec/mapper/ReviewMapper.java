package by.clevertec.mapper;

import by.clevertec.dto.ReviewDto;
import by.clevertec.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toReviewDto(Review review);

//    @Mapping(target = "id", ignore = true)
    Review toReview(ReviewDto reviewDto);
}
