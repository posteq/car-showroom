package by.clevertec.service;

import by.clevertec.entity.CarShowroom;
import by.clevertec.repository.CarShowroomRepository;

import java.util.List;

public class CarShowroomService {
    private final CarShowroomRepository carShowroomRepository = new CarShowroomRepository();

    public void addShowroom(CarShowroom showroom) {
        carShowroomRepository.create(showroom);
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
