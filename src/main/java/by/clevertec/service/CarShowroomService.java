package by.clevertec.service;

import by.clevertec.dto.CarShowroomDto;
import by.clevertec.entity.CarShowroom;
import by.clevertec.exception.ShowroomNotFoundException;
import by.clevertec.mapper.CarShowroomMapper;
import by.clevertec.repository.CarShowroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarShowroomService {

    private final CarShowroomRepository carShowroomRepository;
    private final CarShowroomMapper carShowroomMapper;

    @Transactional
    public CarShowroomDto create(CarShowroomDto carShowroomDto) {
        CarShowroom carShowroom = carShowroomRepository.save(carShowroomMapper.toCarShowroom(carShowroomDto));
        return carShowroomMapper.toCarShowroomDto(carShowroom);
    }

    @Transactional
    public CarShowroomDto update(Long id,CarShowroomDto showroomDto) {
        return carShowroomMapper.toCarShowroomDto(
                carShowroomRepository.findById(id)
                    .map(showroom -> {
                        CarShowroom updatedShowroomDTO = carShowroomMapper.toCarShowroom(showroomDto);
                        showroom.setName(updatedShowroomDTO.getName());
                        showroom.setAddress(updatedShowroomDTO.getAddress());
                        return carShowroomRepository.save(showroom);
                    })
                .orElseThrow(() -> new ShowroomNotFoundException(id))
        );
    }

    @Transactional
    public void delete(Long id) {
        carShowroomRepository.deleteById(id);
    }

    @Transactional
    public CarShowroomDto findById(Long id) {
        return carShowroomMapper.toCarShowroomDto(carShowroomRepository.findById(id)
                .orElseThrow(() -> new ShowroomNotFoundException(id))
        );
    }

    @Transactional
    public List<CarShowroomDto> findAll() {
        return carShowroomMapper.toCarShowroomDtoList(carShowroomRepository.findAll());
    }

}
