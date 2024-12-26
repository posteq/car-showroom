package by.clevertec.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    @NotBlank
    @Size(min = 1,max = 32, message = "Name should be between 1 and 32 characters")
    private String name;

    @Positive(message = "Price must be greater than 0")
    @Min(value = 2020 , message = "Date of registration should be greater than 2020")
    @Max(value = 2050 , message = "Date of registration should be less than 2050")
    private Integer dateOdRegistration;

    @NotEmpty
    private List<@NotBlank String> contacts;
}
