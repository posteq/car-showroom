package by.clevertec.service;

import by.clevertec.dto.CarShowroomDto;
import by.clevertec.entity.CarShowroom;
import by.clevertec.exception.CategoryNotFoundException;
import by.clevertec.mapper.CarShowroomMapper;
import by.clevertec.repository.CarShowroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarShowroomService {
    private final CarShowroomRepository carShowroomRepository;
    private final CarShowroomMapper carShowroomMapper;

    public CarShowroomDto addShowroom(CarShowroomDto carShowroomDto) {
        CarShowroom carShowroom = carShowroomRepository.save(carShowroomMapper.toCarShowroom(carShowroomDto));
        return carShowroomMapper.toCarShowroomDto(carShowroom);
    }

    public CarShowroomDto update(Long id,CarShowroomDto showroomDto) {
        return carShowroomMapper.toCarShowroomDto(
                carShowroomRepository.findById(id)
                    .map(showroom -> {
                        CarShowroom updatedShowroomDTO = carShowroomMapper.toCarShowroom(showroomDto);
                        showroom.setName(updatedShowroomDTO.getName());
                        showroom.setAddress(updatedShowroomDTO.getAddress());
                        return carShowroomRepository.save(showroom);
                    })
                .orElseThrow(() -> new CategoryNotFoundException("Showroom not found with id : " + id))
        );
    }

    public void delete(Long id) {
        carShowroomRepository.deleteById(id);
    }

    public CarShowroomDto getById(Long id) {
        return carShowroomMapper.toCarShowroomDto(carShowroomRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Showroom not found with id : " + id))
        );
    }

    public List<CarShowroomDto> getAll() {
        return carShowroomMapper.toCarShowroomDtoList(carShowroomRepository.findAll());
    }

    public List<CarShowroomDto> getShowroomWithCars() {
        return carShowroomRepository.findAllShowroomWithCars();
    }
}
