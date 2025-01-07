package by.clevertec.mapper;

import by.clevertec.dto.CarShowroomDto;
import by.clevertec.entity.CarShowroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarShowroomMapper {

    CarShowroomDto toCarShowroomDto(CarShowroom carShowroom);

    @Mapping(target = "cars", ignore = true)
    CarShowroom toCarShowroom(CarShowroomDto carShowroomDto);

    List<CarShowroomDto> toCarShowroomDtoList(List<CarShowroom> carShowrooms);

}

