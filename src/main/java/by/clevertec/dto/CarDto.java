package by.clevertec.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CarDto(
        Long id,
        @NotBlank String model,
        @NotBlank String brand,
        @Min(value = 1900) @Max(value = 2050) int year,
        @Positive double price,
        @NotNull CategoryDto category,
        @NotNull CarShowroomDto showroom) {
}

