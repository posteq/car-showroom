package by.clevertec.mapper;

import org.mapstruct.Mapper;
import by.clevertec.dto.CarDto;
import by.clevertec.entity.Car;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CarShowroomMapper.class})
public interface CarMapper {

    List<CarDto> toCarDtoList(List<Car> cars);

    @Mapping(target = "review", ignore = true)
    Car toCar(CarDto carDto);

    CarDto toCarDto(Car car);

}
