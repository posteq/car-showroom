package by.clevertec.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    @NotBlank
    @Size(min = 1,max = 300, message = "Name should be between 1 and 300 characters")
    private String content;

    @Positive(message = "Rating should be greatest than 0")
    @Max(value = 5 , message = "Rating should be less than 5")
    private Integer rating;

    private ClientDto client;

    private CarDto car;
}
