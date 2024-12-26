package by.clevertec.dto;

import by.clevertec.entity.CarShowroom;
import by.clevertec.entity.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CarDto {

    @NotBlank
    @Size(min = 1,max = 32, message = "Name model should be between 1 and 32 characters")
    private String model;

    @NotBlank
    @Size(min = 1,max = 32, message = "Name brand should be between 1 and 32 characters")
    private String brand;

    @Positive(message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1920,message = "Year of release should be grates that 1920")
    @Max(value = 2050,message = "Year of release should be less that 2050")
    private Integer yearsOfRelease;

    private CategoryDto category;

    private CarShowroomDto showroom;
}
