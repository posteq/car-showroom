package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.repository.CarRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository = new CarRepository();


    public Car addCar(Car car) {
        return carRepository.create(car);
    }

    public void updateCar(Car car) {
        carRepository.update(car);
    }

    public void deleteCar(Car car) {
        carRepository.delete(car.getId());
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

     
    public void assignCarToShowroom(Car car, CarShowroom carShowroom) {
        carRepository.assignToShowroom(car, carShowroom);
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
