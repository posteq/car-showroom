package by.clevertec.service;

import by.clevertec.dto.CarDto;
import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.exception.CarNotFoundException;
import by.clevertec.exception.ShowroomNotFoundException;
import by.clevertec.mapper.CarMapper;
import by.clevertec.repository.CarRepository;
import by.clevertec.repository.CarShowroomRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository ;
    private final CarShowroomRepository carShowroomRepository;
    private final CarMapper carMapper;


    public CarDto create(CarDto carDto) {
        Car car = carRepository.save(carMapper.toCar(carDto));
        return carMapper.toCarDto(car);
    }

    public void updateCar(Long id,Car car) {
        carRepository.findById(id)
                .orElseThrow(()->new CarNotFoundException("Car not found with id : " + id ));
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(()->new CarNotFoundException("Car not found with id : " + id ));
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

     
    public void assignCarToShowroom(Long carId, Long showroomId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id : " + carId));
        CarShowroom carShowroom = carShowroomRepository.findById(showroomId)
                .orElseThrow(() -> new ShowroomNotFoundException("Showroom not found with id : " + showroomId));
        car.setShowroom(carShowroom);
        carRepository.save(car);
    }

    public List<Car> findCarsByFilters(String brand, String category, Integer year, Double minPrice, Double maxPrice){
        return carRepository.findByFilter(brand,year,category,minPrice,maxPrice);
    }

    public List<Car> findCarsSortedByPrice(boolean forwardOrder) {
        return carRepository.sortByPrice(forwardOrder);
    }

    public List<Car> findCarsWithPagination(int pageNumber, int pageSize) {
        return carRepository.findCarsWithPagination(pageNumber, pageSize);
    }

}
