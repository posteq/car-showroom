package by.clevertec.repository;

import by.clevertec.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByFilter(String brand, Integer year, String category, Double minPrice, Double maxPrice);
}
