package by.clevertec.mapper;

import by.clevertec.dto.CarDto;
import by.clevertec.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

//    @Mapping(target = "id",ignore = true)
//    @Mapping(target = "review",ignore = true)
    Car toCar(CarDto carDto);


    CarDto toCarDto(Car car);
}
