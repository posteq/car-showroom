package by.clevertec.mapper;

import by.clevertec.dto.CarDto;
import by.clevertec.dto.CarShowroomDto;
import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarShowroomMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "cars", ignore = true)
    CarShowroom toCarShowroom(CarShowroomDto carShowroomDto);

    CarShowroomDto toCarShowroomDto(CarShowroom carShowroom);

    List<CarShowroomDto> toCarShowroomDtoList(List<CarShowroom> carShowroom);

}
