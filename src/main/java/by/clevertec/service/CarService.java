package by.clevertec.service;

import by.clevertec.dto.CarDto;
import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.exception.CarNotFoundException;
import by.clevertec.exception.ShowroomNotFoundException;
import by.clevertec.mapper.CarMapper;
import by.clevertec.repository.CarRepository;
import by.clevertec.repository.CarShowroomRepository;
import by.clevertec.repository.spicification.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository ;
    private final CarShowroomRepository carShowroomRepository;
    private final CarMapper carMapper;


    @Transactional
    public CarDto create(CarDto carDto) {
        Car car = carRepository.save(carMapper.toCar(carDto));
        return carMapper.toCarDto(car);
    }

    @Transactional
    public CarDto update(Long id,CarDto carDto) {
        return carMapper.toCarDto(
                carRepository.findById(id)
                        .map(car -> {
                            Car updatedCarDTO = carMapper.toCar(carDto);
                            car.setModel(updatedCarDTO.getModel());
                            car.setBrand(updatedCarDTO.getBrand());
                            car.setPrice(updatedCarDTO.getPrice());
                            car.setYear(updatedCarDTO.getYear());
                            car.setCategory(updatedCarDTO.getCategory());
                            car.setShowroom(updatedCarDTO.getShowroom());
                            return carRepository.save(car);
                        })
                        .orElseThrow(() -> new CarNotFoundException(id))
        );
    }

    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Transactional
    public CarDto findById(Long id) {
        return carMapper.toCarDto(carRepository.findById(id)
                .orElseThrow(()->new CarNotFoundException(id ))
        );
    }

    @Transactional
    public List<CarDto> findAll() {
        return carMapper.toCarDtoList(carRepository.findAll());
    }

    @Transactional
    public void assignCarToShowroom(Long carId, Long showroomId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
        CarShowroom carShowroom = carShowroomRepository.findById(showroomId)
                .orElseThrow(() -> new ShowroomNotFoundException(showroomId));
        car.setShowroom(carShowroom);
        carRepository.save(car);
    }

    @Transactional
    public List<CarDto> findCarsByParameters(String brand, Integer year, String category, Double minPrice, Double maxPrice) {
        Specification<Car> spec = CarSpecification.create(brand, year, category, minPrice, maxPrice);

        return carMapper.toCarDtoList(carRepository.findAll(spec));
    }

    @Transactional
    public List<CarDto> findCarsSortedByPrice(boolean forwardOrder) {
        Sort orders = forwardOrder ? Sort.by("price").ascending() : Sort.by("price").descending();
        return carMapper.toCarDtoList(carRepository.findAll(orders));
    }

    @Transactional
    public List<CarDto> findCarsWithPagination(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return carMapper.toCarDtoList(carRepository.findAll(pageRequest).getContent());
    }

}
