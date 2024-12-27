package by.clevertec.service;

import by.clevertec.dto.CarShowroomDto;
import by.clevertec.entity.CarShowroom;
import by.clevertec.repository.CarShowroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarShowroomService {
    private final CarShowroomRepository carShowroomRepository;
    private final

    public CarShowroomDto addShowroom(CarShowroomDto carShowroomDto) {
        return carShowroomRepository.save(carShowroomDto);
    }

    public void updateShowroom(CarShowroom showroom) {
        carShowroomRepository.update(showroom);
    }

    public void deleteShowroom(CarShowroom showroom) {
        carShowroomRepository.delete(showroom.getId());
    }

    public CarShowroom getById(Long id) {
        return carShowroomRepository.findById(id);
    }

    public List<CarShowroom> getAllShowrooms() {
        return carShowroomRepository.findAll();
    }

    public List<CarShowroom> getShowroomWithCars() {
        return carShowroomRepository.findAllShowroomWithCars();
    }
}
